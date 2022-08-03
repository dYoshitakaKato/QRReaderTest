package com.example.qrreadertest

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.qrreadertest.databinding.QRreaderFragmentBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.permissionx.guolindev.PermissionX


class QRreaderFragment : Fragment() {

    private val viewModel by viewModels<QRreaderViewModel>()

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            //Added preview of scanned barcode
            viewModel.onMatchContent(result.text)
        }
        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    companion object {
        fun newInstance() = QRreaderFragment()
    }

    private lateinit var binding: QRreaderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QRreaderFragmentBinding.inflate(inflater, container, false)
        val formats: Collection<BarcodeFormat> =
            listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        binding.barcodeScanner.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        binding.barcodeScanner.initializeFromIntent(Intent())
        binding.barcodeScanner.decodeContinuous(callback)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.onTransit.observe(viewLifecycleOwner, EventObserver  {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            val action = QRreaderFragmentDirections.actionQrReaderFragmentToResultFragment(it)
            findNavController().navigate(action)
        })
    }

    override fun onResume() {
        super.onResume()
        binding.barcodeScanner.resume()
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    Toast.makeText(context, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeScanner.pause()
    }
}