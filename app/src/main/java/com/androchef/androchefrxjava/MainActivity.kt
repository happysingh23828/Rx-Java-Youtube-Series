package com.androchef.androchefrxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*

class MainActivity : AppCompatActivity() {


    companion object {
        private const val TAG = "MainActivityRxJava"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rxJavaBasicsShow()
    }

    //to save all the subscriptions(disposable)
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    private fun rxJavaBasicsShow() {

        //creation of Observable
        val observable: Observable<String> = Observable.just("hello world", "from Androchef")

        //observer for listening data streams.
        val observer = object : Observer<String> {
            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }

            override fun onSubscribe(d: Disposable?) {
                //saving subscription
                compositeDisposable.add(d)
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(t: String?) {
                Log.d(TAG, "onNext data = $t")
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError Exception = ${e?.localizedMessage}")
            }
        }

        observable
                .map { it.toUpperCase(Locale.getDefault()) } //Transformation (Converting to UpperCase)
                .subscribe(observer) //Subscription and execution handled by Observable

    }

    //Cancelling all the subscription when activity is getting destroyed.
    override fun onDestroy() {
        super.onDestroy()

        //Check if it is already destroyed or not.
        if(compositeDisposable.isDisposed.not()){
            compositeDisposable.dispose()
        }
    }

}