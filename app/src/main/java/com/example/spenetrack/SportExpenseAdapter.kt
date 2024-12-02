package com.example.spenetrack

import android.annotation.SuppressLint
import android.content.Context
import android.util.Printer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class SportExpenseAdapter(
    private val context: Context,
    private val items: MutableList<String>,
    private val onDelete:(Int) -> Unit
) : RecyclerView.Adapter<SportExpenseAdapter.SportSpendViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportSpendViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_food_spend, parent, false)
        return SportSpendViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SportSpendViewHolder, position: Int) {
        val data = items[position]
        val splitData = data.split(" - ")

        // Bind data to views
        holder.expenseType.text = splitData[0]
        holder.amount.text = "$${splitData[1]}"

        // Handle delete button click
        holder.deleteIcon.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes") { _, _ ->
                    items.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Item deleted Successfully🤦‍♂️", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class SportSpendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseType: TextView = itemView.findViewById(R.id.expenseType)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)
    }
}
