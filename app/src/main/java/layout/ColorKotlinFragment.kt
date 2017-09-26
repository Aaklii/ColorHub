package layout

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.adapters.ColorKotlinAdapter
import cheetatech.com.colorhub.defines.ColorData
import cheetatech.com.colorhub.listeners.OnItemSelect

class ColorKotlinFragment : Fragment() {

    private var mColorListener: OnItemSelect? = null
    private var mListener: OnFragmentInteractionListener? = null
    private var mList: MutableList<ColorData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_color_kotlin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var mRecyclerView = view?.findViewById(R.id.kotlin_color_recycler_view) as RecyclerView

        var manager = GridLayoutManager(activity.applicationContext, 1)
        with(mRecyclerView){
            layoutManager = manager
            setHasFixedSize(true)
        }
        var adapter = ColorKotlinAdapter(this.mList!!, object : OnItemSelect{
            override fun onAddColor(color: String) {
                mColorListener?.onAddColor(color)
            }

            override fun onItemSelected(position: Int) {
            }

        })

        mRecyclerView.adapter = adapter

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        fun newInstance(mutableList: MutableList<ColorData> ,listener: OnItemSelect ): ColorKotlinFragment {
            val fragment = ColorKotlinFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.setListener(listener)
            fragment.setList(mutableList)
            return fragment
        }
    }

    private fun setList(mutableList: MutableList<ColorData>) {
        this.mList = mutableList
    }

    private fun  setListener(listener: OnItemSelect) {this.mColorListener = listener}
}