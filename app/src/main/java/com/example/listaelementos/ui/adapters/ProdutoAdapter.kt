package com.example.listaelementos.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.listaelementos.R
import com.example.listaelementos.domain.models.Produto
import java.text.NumberFormat
import java.util.Locale

class ProdutoAdapter(contexto: Context) : ArrayAdapter<Produto>(contexto, 0) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v: View
        if (convertView != null) {
            v = convertView
        } else {
            //inflar o layout
            v = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false)
        }

        val item = getItem(position)

        val txt_produto = v.findViewById<TextView>(R.id.txt_item_produto)
        val txt_qtd = v.findViewById<TextView>(R.id.txt_item_qtd)
        val txt_valor = v.findViewById<TextView>(R.id.txt_item_valor)
        val img_produto = v.findViewById<ImageView>(R.id.img_item_foto)

        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))

        txt_qtd.text = item?.quantidade.toString()
        txt_produto.text = item?.nome
        txt_valor.text = f.format(item?.valor)
        if (item?.foto != null){
            img_produto .setImageBitmap(item.foto)
        }
        return v
    }
}