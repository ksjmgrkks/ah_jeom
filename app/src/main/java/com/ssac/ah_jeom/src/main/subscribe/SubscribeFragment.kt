package com.ssac.ah_jeom.src.main.subscribe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ssac.ah_jeom.R
import com.ssac.ah_jeom.databinding.FragmentSubscribeBinding
import com.ssac.ah_jeom.src.main.MainActivity
import com.ssac.ah_jeom.src.main.subscribe.adapter.SubscribeIllustrationViewpagerAdapter
import com.ssac.ah_jeom.src.main.subscribe.adapter.SubscribeMainViewpagerAdapter
import com.ssac.ah_jeom.src.main.subscribe.models.GetSubscribeResponse
import com.ssac.ah_jeom.src.main.subscribe.models.SubscribeIllustrationData
import com.ssac.ah_jeom.src.main.subscribe.models.SubscribeImageData
import com.ssac.ah_jeom.src.main.subscribe.subscribeArtist.SubscribeArtistActivity
import com.ssac.ah_jeom.src.profile.ProfileActivity
import com.ssac.ah_jeom.src.search.SearchActivity

class SubscribeFragment : Fragment(), SubscribeFragmentView {

    private var binding: FragmentSubscribeBinding? = null

    private val illustrationData: MutableList<SubscribeIllustrationData> = mutableListOf()
    private val imageData: MutableList<SubscribeImageData> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubscribeBinding.inflate(inflater, container, false)

        setSubscribeIllustrationViewpager()


        binding!!.fragmentSubscribeSubscribeArtistButton.setOnClickListener {
            val intent = Intent(requireActivity(), SubscribeArtistActivity::class.java)
            startActivity(intent)
            (activity as MainActivity).overridePendingTransition(
                R.anim.activity_fade_in,
                R.anim.activity_fade_out
            )
        }

        binding!!.fragmentSubscribeSearchButton.setOnClickListener {
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
            (activity as MainActivity).overridePendingTransition(
                R.anim.activity_fade_in,
                R.anim.activity_fade_out
            )
        }

        binding!!.fragmentSubscribeProfileButton.setOnClickListener {
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
            (activity as MainActivity).overridePendingTransition(
                R.anim.activity_fade_in,
                R.anim.activity_fade_out
            )
        }

        SubscribeService(this).tryGetSubscribe()

        return binding!!.root
    }

    // 뷰 페이저에 들어갈 아이템
    private fun getImageList(): ArrayList<Int> {
        return arrayListOf(
            R.drawable.best_artist_illustration_image,
            R.drawable.recently_artist_illustration_image,
            R.drawable.soaring_artist_illustration_image
        )
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setSubscribeIllustrationViewpager() {
        setIllustrationImageData()

        val data: MutableList<SubscribeIllustrationData> = illustrationData
        var adapter = SubscribeIllustrationViewpagerAdapter(requireActivity())
        adapter.item = data
        binding!!.fragmentSubscribeIllustrationViewPager.adapter = adapter
        binding!!.fragmentSubscribeIllustrationViewPager.orientation =
            ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
        adapter.notifyDataSetChanged()

        // 뷰페이저 인디케이터
        val dotsIndicator = binding?.fragmentSubscribeIllustrationIndicator
        val viewPager = binding?.fragmentSubscribeIllustrationViewPager
        viewPager?.adapter = adapter
        dotsIndicator?.setViewPager2(viewPager!!)
    }

    private fun setSubscribeMainViewpager() {
        // 구독 프래그먼트 메인 뷰페이저
        val subscribeImageData: MutableList<SubscribeImageData> = imageData
        var subscribeImageAdapter = SubscribeMainViewpagerAdapter()
        subscribeImageAdapter.listData = subscribeImageData
        binding?.fragmentSubscribeMainViewpager?.adapter = subscribeImageAdapter

        // 뷰페이저 인디케이터
        val dotsIndicator = binding?.fragmentSubscribeMainIndicator
        val viewPager = binding?.fragmentSubscribeMainViewpager
        viewPager?.adapter = subscribeImageAdapter
        dotsIndicator?.setViewPager2(viewPager!!)
    }

    private fun setIllustrationImageData() {
        illustrationData.add(SubscribeIllustrationData(R.drawable.best_artist_illustration_image, "인싸 작가요", "베스트 작가 보기"))
        illustrationData.add(SubscribeIllustrationData(R.drawable.recently_artist_illustration_image, "응애 작가요", "최근 작가 보기"))
        illustrationData.add(SubscribeIllustrationData(R.drawable.soaring_artist_illustration_image, "시금치 빠워", "급상승 작가 보기"))
    }

    override fun onGetSubscribeSuccess(response: GetSubscribeResponse) {
        if (response.isSuccess) {
            response.result.forEach {
                imageData.add(SubscribeImageData(it.img))
            }
            setSubscribeMainViewpager()
        }
    }

    override fun onGetSubscribeFailure(message: String) {
        Toast.makeText(context, "오류 : $message", Toast.LENGTH_SHORT).show()
    }

}