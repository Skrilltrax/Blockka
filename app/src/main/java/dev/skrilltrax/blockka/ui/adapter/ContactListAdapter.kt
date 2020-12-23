package dev.skrilltrax.blockka.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import dev.skrilltrax.blockka.databinding.ItemContactBinding
import dev.skrilltrax.sqldelight.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListAdapter(private var contactList: List<Contact>) :
  RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

  init {
    setHasStableIds(true)
  }

  private var selectionTracker: SelectionTracker<Long>? = null
  fun requireSelectionTracker() = selectionTracker!!

  val selectedContacts get() = requireSelectionTracker().selection.map { contactList[it.toInt()] }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
    val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ContactViewHolder(binding.root, binding)
  }

  override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
    holder.bind(contactList[position], selectionTracker?.isSelected(position.toLong()) ?: false)
  }

  override fun getItemCount(): Int {
    return contactList.size
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  private var onItemClickedListener: ((holder: ContactViewHolder, item: Contact) -> Unit)? = null
  fun onItemClicked(listener: (holder: ContactViewHolder, item: Contact) -> Unit): ContactListAdapter {
    check(onItemClickedListener == null) { "Only a single listener can be registered for onItemClicked" }
    onItemClickedListener = listener
    return this
  }

  private var onSelectionChangedListener: ((selection: Selection<Long>) -> Unit)? = null
  fun onSelectionChanged(listener: (selection: Selection<Long>) -> Unit): ContactListAdapter {
    check(onSelectionChangedListener == null) { "Only a single listener can be registered for onSelectionChanged" }
    onSelectionChangedListener = listener
    return this
  }

  fun updateList(list: List<Contact>) {
    this.contactList = list
    notifyDataSetChanged()
  }

  fun makeSelectable(recyclerView: RecyclerView) {
    selectionTracker = SelectionTracker.Builder(
      "mySelection",
      recyclerView,
      StableIdKeyProvider(recyclerView),
      ContactDetailsLookup(recyclerView),
      StorageStrategy.createLongStorage()
    ).withSelectionPredicate(
      SelectionPredicates.createSelectAnything()
    ).build()

    selectionTracker!!.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
      override fun onSelectionChanged() {
        this@ContactListAdapter.onSelectionChangedListener?.invoke(requireSelectionTracker().selection)
      }
    })
  }

  inner class ContactViewHolder(itemView: View, private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(itemView) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun bind(contact: Contact, isSelected: Boolean) {
      with(binding) {
        if (contact.name.isNullOrEmpty()) {
          subtitleText.visibility = View.GONE
          titleText.text = contact.number
        } else {
          subtitleText.visibility = View.VISIBLE
          titleText.text = contact.name
          subtitleText.text = contact.number
        }
      }

      if (!contact.image_uri.isNullOrEmpty()) {
        val imageUri = Uri.parse(contact.image_uri)
        binding.imageview.load(imageUri) {
          transformations(CircleCropTransformation())
        }
      }

      itemView.setOnClickListener { onItemClickedListener?.invoke(this, contact) }
      itemView.isSelected = isSelected
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
      object : ItemDetailsLookup.ItemDetails<Long>() {
        override fun getPosition(): Int = adapterPosition
        override fun getSelectionKey(): Long? = itemId
      }
  }

  class ContactDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
      val view = recyclerView.findChildViewUnder(event.x, event.y)
      if (view != null) {
        return (recyclerView.getChildViewHolder(view) as ContactViewHolder).getItemDetails()
      }
      return null
    }
  }
}
