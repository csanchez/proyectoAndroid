package com.iis.app.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.iis.app.databinding.LoadingViewBinding
import javax.inject.Singleton





class LoadingView @JvmOverloads constructor( context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var _binding: LoadingViewBinding
    private val binding get() = _binding!!


    init {
        // Init View
        //val rootView = (getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.loading_view, this, true)

        val inflater = (getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        _binding = LoadingViewBinding.inflate(LayoutInflater.from(context), this,true)

    }


  /*
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.d("LoadingView","onInterceptTouchEvent")
        return true
    }


   */


}

object LoadingViewSingleton {

    private lateinit var loadingView: LoadingView
    private var isShow = false
    var time = 1500L

    public fun setView(loadingView: LoadingView){
        this.loadingView = loadingView
    }

    public fun show(){
        this.loadingView.visibility = View.VISIBLE
        this.isShow = true
        Log.d("show()",this.isShow.toString())
    }

    public fun hide(){
        this.loadingView.visibility = View.GONE
        this.isShow = false
    }

    public fun isShow() : Boolean{
        return this.isShow
    }
}