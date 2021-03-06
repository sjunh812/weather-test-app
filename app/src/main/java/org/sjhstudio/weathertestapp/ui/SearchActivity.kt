package org.sjhstudio.weathertestapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.sjhstudio.weathertestapp.R
import org.sjhstudio.weathertestapp.databinding.ActivitySearchBinding
import org.sjhstudio.weathertestapp.model.Addresses
import org.sjhstudio.weathertestapp.ui.adapter.SearchAdapter
import org.sjhstudio.weathertestapp.ui.adapter.SearchAdapterCallback
import org.sjhstudio.weathertestapp.util.BaseActivity
import org.sjhstudio.weathertestapp.util.Constants.RESULT_SEARCH_AREA
import org.sjhstudio.weathertestapp.viewmodel.SearchViewModel

@AndroidEntryPoint
class SearchActivity: BaseActivity() {

    private val binding: ActivitySearchBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_search) }
    private val searchVm: SearchViewModel by viewModels()
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            searchBtn.setOnClickListener {
                val input = searchEt.text.toString()

                if(input.trim().isEmpty()) {
                    Snackbar.make(binding.searchRv, "내용을 입력해주세요.", 1000).show()
                } else {
                    searchVm.getAddressList(input)
                }
            }
            searchRv.apply {
                searchAdapter.apply {
                    setOnSearchAdapterCallback(object: SearchAdapterCallback {
                        override fun onSelected(item: Addresses) {
                            val intent = Intent().apply { putExtra(RESULT_SEARCH_AREA, item) }
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    })
                }
                adapter = searchAdapter
                layoutManager = LinearLayoutManager(this@SearchActivity)
            }
        }
        observeAddressList()
    }

    private fun observeAddressList() {
        searchVm.addrList.observe(this) { list ->
            if(list.isNotEmpty()) {
                println("xxx $list")
                searchAdapter.setItems(list as ArrayList<Addresses>)
            } else {
                Snackbar.make(binding.searchRv, "검색결과가 없습니다.", 1000).show()
            }
        }
    }

}