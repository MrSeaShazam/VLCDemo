package com.example.vlcdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.vlcdemo.databinding.FragmentVideoPlayerBinding
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

class VideoPlayerFragment : Fragment() {
    private val binding: FragmentVideoPlayerBinding by viewBinding()
    private val args: VideoPlayerFragmentArgs by navArgs()
    private var mMediaPlayer: MediaPlayer? = null
    private var mLibVLC: LibVLC? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {

        val videolayout = VLCVideoLayout(requireContext())

        binding.apply {
            mLibVLC = LibVLC(requireContext(), ArrayList<String>().apply {
                add("--no-drop-late-frames")
                add("--no-skip-frames")
                add("--rtsp-tcp")
                add("-vvv")
            })
            mMediaPlayer = MediaPlayer(mLibVLC)
            mMediaPlayer?.attachViews(binding.videoPlayerVp, null, false,false)
            //mMediaPlayer?.attachViews(videolayout, null, false,false)
            try {
                Media(mLibVLC, args.url.toUri()).apply {
                    setHWDecoderEnabled(true, false)
                    // addOption(":network-caching=150");
                    // addOption(":clock-jitter=0");
                    // addOption(":clock-synchro=0");
                    mMediaPlayer?.media = this
                }.release()
                mMediaPlayer?.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mMediaPlayer?.stop()
        mMediaPlayer?.detachViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.release()
        mLibVLC?.release()
    }

}