package com.example.photojora.fragments

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photojora.R
import com.example.photojora.adapters.GroceryListRecyclerViewAdapter
import com.example.photojora.models.Grocery
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_photo.*

class PhotoFragment : Fragment() {

    private lateinit var mImagePhoto: ImageView
    private lateinit var mImageUri: Uri
    private lateinit var mAdapter: GroceryListRecyclerViewAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = LayoutInflater.from(context).inflate(R.layout.fragment_photo, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initApp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            try {
                // load the image into ImageView
                if (Build.VERSION.SDK_INT < 28) {
                    val thumbnail = MediaStore.Images.Media.getBitmap(
                        activity?.contentResolver, mImageUri
                    )

                    mImagePhoto.setImageBitmap(thumbnail)
                } else {
                    val source =
                        ImageDecoder.createSource(activity?.contentResolver!!, mImageUri)
                    val bitmap = ImageDecoder.decodeBitmap(source)

                    mImagePhoto.setImageBitmap(bitmap)
                }

                // load grocery list after set up the image
                updateGroceryList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            // go back to History Fragment
            if (requestCode == CAMERA_REQUEST) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_fragment, HistoryFragment())
                    ?.commit()
                activity?.main_bottom_navigation_view?.menu?.findItem(R.id.bottom_navigation_view_history)
                    ?.isChecked = true
            }
        }
    }

    private fun initApp() {
        mImagePhoto = image_view_photo

        initRecyclerView()
        openCamera(CAMERA_REQUEST)
        retakePhoto()
        showRecipes()
    }

    private fun openCamera(requestCode: Int) {
        // open the native Camera App to take a photo
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")
        mImageUri = activity?.contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
        )!!

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri)
        startActivityForResult(
            intent,
            requestCode
        )
    }

    private fun retakePhoto() = image_view_record.setOnClickListener {
        openCamera(RETAKE_CAMERA_REQUEST)
    }

    private fun initRecyclerView() {
        mRecyclerView = recycler_view_grocery_list
        mAdapter = GroceryListRecyclerViewAdapter(context!!)

        mRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        mRecyclerView.adapter = mAdapter
    }

    private fun updateGroceryList() {
        // clear the list before the initialization
        mAdapter.clearList()

        // init the list
        mAdapter.addGrocery(Grocery("хлеб", true))
        mAdapter.addGrocery(Grocery("сыр", true))
        mAdapter.addGrocery(Grocery("курица", true))
        mAdapter.addGrocery(Grocery("огурцы", true))
        mAdapter.addGrocery(Grocery("яйца", true))
        mAdapter.addGrocery(Grocery("колбаса", true))
        mAdapter.addGrocery(Grocery("помидоры", true))
        mAdapter.addGrocery(Grocery("икра", true))
        mAdapter.addGrocery(Grocery("чеснок", true))
        mAdapter.addGrocery(Grocery("баклажан", true))
        mAdapter.addGrocery(Grocery("арбуз", true))
        mAdapter.addGrocery(Grocery("зелёный горошек", true))
        mAdapter.addGrocery(Grocery("тыква", true))
        mAdapter.addGrocery(Grocery("масло", true))

        mAdapter.notifyDataSetChanged()
    }

    private fun showRecipes() = button_show_recipes.setOnClickListener {
        // check for selected foodstuffs
        if (mAdapter.getCountOfSelectedItems() > 0) {
            // go to Food Fragment
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, FoodFragment())
                ?.commit()
            activity?.main_bottom_navigation_view?.menu?.findItem(R.id.bottom_navigation_view_food)
                ?.isChecked = true
        } else {
            Toast.makeText(context, "Вы не выбрали ни одного продукта!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val CAMERA_REQUEST = 127 // we use this to run the native Camera App for the first time
        private const val RETAKE_CAMERA_REQUEST = 128 // we use this to retake the photo
    }
}