package dev.skrilltrax.blockka.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.skrilltrax.blockka.databinding.ItemContactBinding
import dev.skrilltrax.sqldelight.Contact

class ContactListAdapter(private var contactList: List<Contact>, val onClick: (Contact) -> Unit) :
  RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemContactBinding.inflate(inflater)

    return ContactViewHolder(binding.root, binding)
  }

  override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
    holder.bind(contactList[position])
  }

  override fun getItemCount(): Int {
    return contactList.size
  }

  fun updateList(list: List<Contact>) {
    this.contactList = list
    notifyDataSetChanged()
  }

  inner class ContactViewHolder(itemView: View, private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(contact: Contact) {
      with(binding) {
        titleText.text = contact.number
        subtitleText.text = contact.name
      }

      itemView.setOnClickListener { onClick(contact) }
    }
  }
}
