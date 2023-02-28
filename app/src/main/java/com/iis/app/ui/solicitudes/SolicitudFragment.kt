package com.iis.app.ui.solicitudes

import android.graphics.Color
import com.iis.app.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.iis.app.data.model.Condition
import com.iis.app.data.model.Solicitud
import com.iis.app.data.model.Step
import com.iis.app.databinding.FragmentSolicitudBinding
import com.iis.app.ui.components.ColorGenerator
import com.iis.app.ui.components.TextDrawable


/**
 * A simple [Fragment] subclass.
 * Use the [SolicitudFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SolicitudFragment : Fragment() {


    private val args: SolicitudFragmentArgs by navArgs()
    private lateinit var solicitudViewModel: SolicitudesViewModel
    private lateinit var _binding: FragmentSolicitudBinding

    private val binding get() = _binding!!
    private val tagg="TRAMITES FRAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view =  inflater.inflate(R.layout.fragment_solicitud, container, false)


        solicitudViewModel = ViewModelProvider(requireActivity(), SolicitudesViewModelFactory()).get(SolicitudesViewModel::class.java)

        _binding = FragmentSolicitudBinding.inflate(inflater, container, false)
        val root: View = binding.root



        solicitudViewModel.solicitudesResult.observe(viewLifecycleOwner, Observer {
            val solicitudesState = it ?: return@Observer


            if (solicitudesState.success != null) {
                with(view) {
                    val solicitudStatusView : TextView =  binding.status
                    val solicitudStartAtView : TextView =  binding.startedAt
                    val solicitudNumView : TextView =  binding.numSol
                    val solicitudCurrentStepView : ImageView =  binding.solicitudProgress



                    val tramiteDepartmentsView:TextView = binding.departments
                    val tramiteNameView: TextView =  binding.tramiteName
                    val solicitudNameView: TextView =  binding.etapaName
                    val solicitudDescriptionView: TextView =  binding.etapaDescription
                    val dataList: RecyclerView =  binding.solicitudConditionsList

                    val emptyStep = Step("","","",listOf<Condition>())
                    var solicitud = Solicitud(-1,"","","","","","","", emptyStep,0,0,0,"","","","","#FFFFFF")



                    if (args.solicitudPosition==-1){
                         solicitud =solicitudesState.success[solicitudesState.success.size-1]
                    }else{
                         solicitud =solicitudesState.success[args.solicitudPosition]
                    }

                    val currentStep = solicitud.currentStep
                    val conditions = currentStep.conditions





                    val drawable = TextDrawable.builder().buildRound(""+solicitud.currentStepNumber+"/"+solicitud.totalSteps, Color.parseColor(solicitud.color.trim()))
                    solicitudCurrentStepView.setImageDrawable(drawable)


                    solicitudStatusView.text = solicitud.statusName
                    solicitudStartAtView.text = solicitud.startedAt
                    solicitudNumView.text = solicitud.tramiteUserNumber

                    tramiteNameView.text = solicitud.tramiteName
                    tramiteDepartmentsView.text = solicitud.departments

                    solicitudNameView.text = currentStep.name
                    solicitudDescriptionView.text = currentStep.descripcion


                    val radius = resources.getDimension(R.dimen.status_corner_radius)
                    val shapeAppearanceModel = ShapeAppearanceModel()
                        .toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED, radius.toFloat())
                        .build()

                    val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)

                    when (solicitud.status) {
                        "0" -> shapeDrawable.setFillColor(ContextCompat.getColorStateList(requireContext(),R.color.status_0));
                        "1" -> shapeDrawable.setFillColor(ContextCompat.getColorStateList(requireContext(),R.color.status_1));
                        "2" -> shapeDrawable.setFillColor(ContextCompat.getColorStateList(requireContext(),R.color.status_2));
                        "3" -> shapeDrawable.setFillColor(ContextCompat.getColorStateList(requireContext(),R.color.status_3));
                        "4" -> shapeDrawable.setFillColor(ContextCompat.getColorStateList(requireContext(),R.color.status_4));
                        else -> { // Note the block
                            shapeDrawable.setFillColor(ContextCompat.getColorStateList(requireContext(),R.color.status_0));
                        }
                    }


                   // shapeDrawable.setStroke(2.0f, ContextCompat.getColor(this,R.color....));

                    ViewCompat.setBackground(solicitudStatusView, shapeDrawable)



                    dataList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    dataList.adapter = SolicitudesConditionsRecyclerViewAdapter(conditions)

                }
            }
        })



        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SolicitudFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SolicitudFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}