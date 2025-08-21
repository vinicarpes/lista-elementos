package com.example.listaelementos.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listaelementos.databinding.RecyclerViewItemBinding
import com.example.listaelementos.domain.models.Produto

class ProdutoAdapter(private val context: Context, private var produtos: List<Produto>) :
    RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {

    class ViewHolder(private val recycler_view_produtos: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(recycler_view_produtos.root) {
        fun bind(prod: Produto) {
            recycler_view_produtos.txtItemProduto.text = prod.nome
            recycler_view_produtos.txtItemQtd.text = prod.quantidade.toString()
            recycler_view_produtos.txtItemValor.text = prod.valor.toString()
            recycler_view_produtos.imgItemFoto.setImageResource(android.R.drawable.ic_menu_camera)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RecyclerViewItemBinding.inflate(
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