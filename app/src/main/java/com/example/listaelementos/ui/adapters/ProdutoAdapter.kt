package com.example.listaelementos.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listaelementos.databinding.RecyclerViewItemBinding
import com.example.listaelementos.domain.models.Produto

class ProdutoAdapter(private val context: Context) :
    RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {
    private val produtos: MutableList<Produto> = mutableListOf()
    class ViewHolder(private val recyclerViewProdutos: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(recyclerViewProdutos.root) {
        fun bind(prod: Produto) {
            recyclerViewProdutos.txtItemProduto.text = prod.nome
            recyclerViewProdutos.txtItemQtd.text = prod.quantidade.toString()
            recyclerViewProdutos.txtItemValor.text = prod.valor.toString()
            recyclerViewProdutos.imgItemFoto.setImageResource(android.R.drawable.ic_menu_camera)
        }
    }

    fun setList(produtos: List<Produto>){
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
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
}