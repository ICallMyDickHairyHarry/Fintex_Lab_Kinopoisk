package com.example.fintexlabkinopoisk

import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.text.bold
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.fintexlabkinopoisk.network.Film
import com.example.fintexlabkinopoisk.popular.FilmLinearAdapter
import com.example.fintexlabkinopoisk.popular.KinopoiskApiStatus
import java.util.*

// load image with coil library
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_baseline_broken_image)
        }
    }
}

// pass data to list adapter
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<Film>?) {
    val adapter = recyclerView.adapter as FilmLinearAdapter
    adapter.submitList(data)
}

// set short description of film item, e.g. "Фантастика (2022)"
@BindingAdapter("descriptionText")
fun bindText(textView: TextView, film: Film?) {
    val genre = film?.genres?.get(0)?.get("genre")?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
    textView.text = textView.resources.getString(R.string.card_description, genre, film?.year)
}

// set text for single information point (genre)
@BindingAdapter("infoGenreText")
fun bindGenreText(textView: TextView, infoList: List<Map<String,String>>?) {
    // convert list of maps to string
    val infoText = infoList?.map { it["genre"] }?.joinToString(", ")
    infoText?.let {
        // make string partially bold
        val decoratedString = SpannableStringBuilder()
            .bold { append(textView.resources.getString(R.string.genres)) }
            .append(" ").append(infoText)
        textView.text = decoratedString
    }
}

// set text for single information point (country)
@BindingAdapter("infoCountryText")
fun bindCountryText(textView: TextView, infoList: List<Map<String,String>>?) {
    val infoText = infoList?.map { it["country"] }?.joinToString(", ")
    infoText?.let {
        val decoratedString = SpannableStringBuilder()
            .bold { append(textView.resources.getString(R.string.countries)) }
            .append(" ").append(infoText)
        textView.text = decoratedString
    }
}

// handle network status for popular fragment (loading, error, done)
@BindingAdapter("kinopoiskApiStatus")
fun bindStatus(statusLinearLayout: LinearLayout,
               status: KinopoiskApiStatus?) {

    setStatusContents(statusLinearLayout, status)

    when (status) {
        KinopoiskApiStatus.DONE -> {
            statusLinearLayout.visibility = View.GONE
        }
        // KinopoiskApiStatus.ERROR or KinopoiskApiStatus.LOADING or null
        else -> {
            statusLinearLayout.visibility = View.VISIBLE
        }

    }
}

// handle network status for detail fragment (loading, error, done)
@BindingAdapter("kinopoiskApiStatusDetail")
fun bindStatusDetail(linearLayout: LinearLayout,
                     status: KinopoiskApiStatus?) {

    setStatusContents(linearLayout, status)
    val detailLayout = linearLayout[0]
    val networkLayout = linearLayout[1]

    when (status) {
        KinopoiskApiStatus.DONE -> {
            networkLayout.visibility = View.GONE
            detailLayout.visibility = View.VISIBLE
        }
        // KinopoiskApiStatus.ERROR or KinopoiskApiStatus.LOADING or null
        else -> {
            networkLayout.visibility = View.VISIBLE
            detailLayout.visibility = View.GONE
        }

    }
}

// set contents of network status layout
private fun setStatusContents (statusLinearLayout: LinearLayout,
                       status: KinopoiskApiStatus?) {

    val textError = statusLinearLayout.findViewById<TextView>(R.id.connect_error_text)
    val statusImg = statusLinearLayout.findViewById<ImageView>(R.id.status_image)
    val retryButton = statusLinearLayout.findViewById<Button>(R.id.retry_button)

    when (status) {
        // show only image on loading
        KinopoiskApiStatus.LOADING -> {
            textError.visibility = View.GONE
            retryButton.visibility = View.GONE
            statusImg.setImageResource(R.drawable.loading_animation)
        }
        // show image, message and button on error
        KinopoiskApiStatus.ERROR -> {
            textError.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            statusImg.setImageResource(R.drawable.ic_connection_error)
        }
        else -> {}
    }
}
