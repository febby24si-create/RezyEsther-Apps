package com.example.rezy_esther.home.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rezy_esther.R
import com.example.rezy_esther.data.model.NewsArticle
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class NewsAdapter(
    private val onItemClick: (NewsArticle) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val articles = mutableListOf<NewsArticle>()

    // ─── ViewHolder ───────────────────────────────────────────────────────────

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardRoot: CardView   = itemView.findViewById(R.id.cardNewsItem)
        val ivThumbnail: ImageView = itemView.findViewById(R.id.ivNewsThumbnail)
        val tvSource: TextView   = itemView.findViewById(R.id.tvNewsSource)
        val tvDate: TextView     = itemView.findViewById(R.id.tvNewsDate)
        val tvTitle: TextView    = itemView.findViewById(R.id.tvNewsTitle)
        val tvDesc: TextView     = itemView.findViewById(R.id.tvNewsDescription)
    }

    // ─── Inflate ──────────────────────────────────────────────────────────────

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    // ─── Bind ─────────────────────────────────────────────────────────────────

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]

        // ① Gambar berita (urlToImage)
        if (!article.urlToImage.isNullOrBlank()) {
            holder.ivThumbnail.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_village_illustration)
                .error(R.drawable.ic_app_logo)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .centerCrop()
                .into(holder.ivThumbnail)
        } else {
            // Tidak ada gambar → tampilkan placeholder statis
            holder.ivThumbnail.visibility = View.VISIBLE
            holder.ivThumbnail.setImageResource(R.drawable.ic_village_illustration)
            holder.ivThumbnail.scaleType = ImageView.ScaleType.FIT_CENTER
        }

        // ② Sumber berita (source.name)
        holder.tvSource.text = article.source.name

        // ③ Tanggal publish (publishedAt) — konversi UTC → WIB
        holder.tvDate.text = formatDate(article.publishedAt)

        // ④ Judul berita (title)
        holder.tvTitle.text = article.title

        // ⑤ Ringkasan (description)
        val desc = article.description
        if (!desc.isNullOrBlank()) {
            holder.tvDesc.text = desc
            holder.tvDesc.visibility = View.VISIBLE
        } else {
            holder.tvDesc.visibility = View.GONE
        }

        // Klik item → callback ke Fragment
        holder.cardRoot.setOnClickListener {
            it.animate()
                .scaleX(0.97f).scaleY(0.97f).setDuration(80)
                .withEndAction {
                    it.animate().scaleX(1f).scaleY(1f).setDuration(80).start()
                    onItemClick(article)
                }.start()
        }
    }

    override fun getItemCount(): Int = articles.size

    // ─── DiffUtil update ──────────────────────────────────────────────────────

    fun submitList(newList: List<NewsArticle>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = articles.size
            override fun getNewListSize() = newList.size
            override fun areItemsTheSame(op: Int, np: Int) =
                articles[op].url == newList[np].url
            override fun areContentsTheSame(op: Int, np: Int) =
                articles[op] == newList[np]
        })
        articles.clear()
        articles.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    // ─── Helper: format tanggal ───────────────────────────────────────────────

    /**
     * Input  : "2025-06-07T10:30:00Z"  (UTC dari NewsAPI)
     * Output : "07 Jun 2025 · 17:30"   (WIB, format Indonesia)
     */
    private fun formatDate(raw: String): String {
        return try {
            val sdfIn = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            sdfIn.timeZone = TimeZone.getTimeZone("UTC")

            val sdfOut = SimpleDateFormat("dd MMM yyyy · HH:mm", Locale("id", "ID"))
            sdfOut.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

            val date = sdfIn.parse(raw)
            date?.let { sdfOut.format(it) } ?: raw.take(10)
        } catch (e: Exception) {
            raw.take(10)          // fallback: tampilkan 10 karakter pertama
        }
    }
}