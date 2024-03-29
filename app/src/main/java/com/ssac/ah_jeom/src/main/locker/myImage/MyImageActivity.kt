package com.ssac.ah_jeom.src.main.locker.myImage

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ssac.ah_jeom.R
import com.ssac.ah_jeom.config.BaseActivity
import com.ssac.ah_jeom.databinding.ActivityMyImageBinding
import com.ssac.ah_jeom.src.main.locker.myImage.adapter.MyImageRecyclerAdapter
import com.ssac.ah_jeom.src.main.locker.myImage.models.GetMyImageResponse
import com.ssac.ah_jeom.src.main.locker.myImage.models.MyImageRecyclerData
import com.ssac.ah_jeom.src.search.adapter.RelatedImageRecyclerAdapter
import com.ssac.ah_jeom.src.search.models.RelatedImageRecyclerData

class MyImageActivity : BaseActivity<ActivityMyImageBinding>(ActivityMyImageBinding::inflate), MyImageActivityView {

    val data: MutableList<MyImageRecyclerData> = mutableListOf()

    private var cursor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activityMyImageBackButton.setOnClickListener {
            onBackPressed()
        }

        MyImageService(this).tryGetMyImage(cursor)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }

    private fun setMyImageView(response: GetMyImageResponse) {
        val data: MutableList<MyImageRecyclerData> = data
        var adapter = MyImageRecyclerAdapter(this, response)
        adapter.listData = data
        binding.activityMyImageRecyclerView.adapter = adapter
        binding.activityMyImageRecyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        adapter.notifyDataSetChanged()
    }

    override fun onGetMyImageSuccess(response: GetMyImageResponse) {
        if (response.code == 3014) {
            binding.activityMyImageRecyclerView.visibility = View.GONE
            binding.activityMyImageNoItemText.visibility = View.VISIBLE
        }
        else if (response.isSuccess) {
            binding.activityMyImageRecyclerView.visibility = View.VISIBLE
            binding.activityMyImageNoItemText.visibility = View.INVISIBLE

            data.clear()
            response.result.myimg.forEach {
                data.add(MyImageRecyclerData(it.img))
            }
            setMyImageView(response)
        }
    }

    override fun onGetMyImageFailure(message: String) {
        showCustomToast("오류 : $message")
    }

}