package dev.skrilltrax.blockka.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.skrilltrax.blockka.databinding.ItemContactBinding
import dev.skrilltrax.sqldelight.RecentContact

class RecentContactListAdapter(private var contactList: List<RecentContact>) :
  RecyclerView.Adapter<RecentContactListAdapter.ContactViewHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
    val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ContactViewHolder(binding.root, binding)
  }

  override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
    holder.bind(contactList[position])
  }

  override fun getItemCount(): Int {
    return contactList.size
  }

  private var onItemClickedListener: ((holder: ContactViewHolder, item: RecentContact) -> Unit)? =
    null

  fun onItemClicked(listener: (holder: ContactViewHolder, item: RecentContact) -> Unit): RecentContactListAdapter {
    check(onItemClickedListener == null) { "Only a single listener can be registered for onItemClicked" }
    onItemClickedListener = listener
    return this
  }

  fun updateList(list: List<RecentContact>) {
    this.contactList = list
    notifyDataSetChanged()
  }

  inner class ContactViewHolder(itemView: View, private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(contact: RecentContact) {
      with(binding) {
        if (!contact.name.isNullOrEmpty()) {
          titleText.text = "${contact.name} (${contact.number})"
        } else {
          titleText.text = contact.number
        }
        if (contact.timestamp.isEmpty()) {
          subtitleText.visibility = View.GONE
        } else {
          subtitleText.visibility = View.VISIBLE
          subtitleText.text = contact.timestamp
        }
      }

      itemView.setOnClickListener { onItemClickedListener?.invoke(this, contact) }
    }
  }
}
