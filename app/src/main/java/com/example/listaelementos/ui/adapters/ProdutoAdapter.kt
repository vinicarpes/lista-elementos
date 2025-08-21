package com.example.listaelementos.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listaelementos.R
import com.example.listaelementos.databinding.ListViewItemBinding
import com.example.listaelementos.domain.models.Produto
import java.text.NumberFormat
import java.util.Locale

class ProdutoAdapter(private val context: Context, private var produtos: List<Produto>) :
    RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {

    class ViewHolder(private val listViewItemBinding: ListViewItemBinding) :
        RecyclerView.ViewHolder(listViewItemBinding.root) {
        fun bind(prod: Produto) {
            listViewItemBinding.txtItemProduto.text = prod.nome
            listViewItemBinding.txtItemQtd.text = prod.quantidade.toString()
            listViewItemBinding.txtItemValor.text = prod.valor.toString()
            listViewItemBinding.imgItemFoto.setImageResource(android.R.drawable.ic_menu_camera)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListViewItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val prod = produtos[position]
        holder.bind(prod)
    }

    override fun getItemCount(): Int = produtos.size

    fun updateData(produtos: List<Produto>){
        this.produtos = produtos
        notifyDataSetChanged()
    }
}