package com.example.dinoappv2.miscFragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.media.MediaFormat
import android.media.MediaPlayer
import android.media.MediaPlayer.TrackInfo
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.dinoappv2.R
import com.example.dinoappv2.bottomNav.MainActivity
import com.example.dinoappv2.dataClasses.*
import com.example.dinoappv2.databinding.FragmentDinoArticleBinding
import com.example.dinoappv2.viewModels.DinoArticleViewModel
import com.example.dinoappv2.viewModels.MainViewModel
import com.google.android.material.transition.MaterialSharedAxis
import java.io.*
import java.util.*

const val TAG = "DinoArticleFragment"

class DinoArticleFragment : Fragment() {

    private lateinit var binding: FragmentDinoArticleBinding
    private lateinit var viewModel: DinoArticleViewModel
    private lateinit var dinoSelected: DinosaurEncyclopedia

    private var mediaPlayerList = listOf<MediaPlayer>()

    private lateinit var storyAudioAnimation: AnimationDrawable
    private lateinit var habitatAudioAnimation: AnimationDrawable
    private lateinit var evolutionAudioAnimation: AnimationDrawable
    private lateinit var fossilAudioAnimation: AnimationDrawable

    //allows back button & up button to be overridden during quiz
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    //NOTE: ADD KEYFRAME FOR RESIZABLE VIEWS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dino_article,
            container,
            false
        )

        storyAudioAnimation = binding.activateAudioStory.drawable as AnimationDrawable
        habitatAudioAnimation = binding.activateAudioHabitat.drawable as AnimationDrawable
        evolutionAudioAnimation = binding.activateAudioEvolution.drawable as AnimationDrawable
        fossilAudioAnimation = binding.activateAudioFossil.drawable as AnimationDrawable

        dinoSelected = arguments?.get("dinoSelected") as DinosaurEncyclopedia
        val dinoPosition = dinoSelected.position

        val dinosaurQuizStrings = DinosaurQuizStrings(resources, dinoPosition)
        val quizText = dinosaurQuizStrings.getQuizStrings()
        val quizCorrectAnswers = dinosaurQuizStrings.correctAnswers

        //creating viewModel
        viewModel = ViewModelProvider(this)[DinoArticleViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.sharedViewModel = sharedViewModel

        //strings for layout
        val livedFact = resources.getStringArray(R.array.years_facts_array)[dinoPosition]
        val speedFact = resources.getStringArray(R.array.speed_facts_array)[dinoPosition]
        val nameMeaningFact = resources.getStringArray(R.array.name_meanings_array)[dinoPosition]
        val storyArticle = resources.getStringArray(R.array.story_articles_array)[dinoPosition]
        val habitatArticle = resources.getStringArray(R.array.habitat_articles_array)[dinoPosition]
        val evolutionArticle =
            resources.getStringArray(R.array.evolution_articles_array)[dinoPosition]
        val fossilArticle = resources.getStringArray(R.array.fossil_articles_array)[dinoPosition]

        binding.quizTitle.text = resources.getStringArray(R.array.quiz_names_array)[dinoPosition]

        //set spannable strings for each section of info to allow difficult words to be pressed
        val storyArticleSpan = SpannableString(storyArticle)
        val habitatArticleSpan = SpannableString(habitatArticle)
        val evolutionArticleSpan = SpannableString(evolutionArticle)
        val fossilArticleSpan = SpannableString(fossilArticle)


        val dictionaryList = mutableListOf<DictionaryStrings>()
        for ((i, word) in resources.getStringArray(R.array.dictionary_words).withIndex()) {
            dictionaryList.add(
                DictionaryStrings(
                    word,
                    resources.getStringArray(R.array.dictionary_definitions)[i]
                )
            )
        }
        for (element in dictionaryList) {
            val word = element.word.lowercase(Locale.getDefault())
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    //navigate to the dictionary and display definition of the word
                    exitTransition =
                        MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
                            duration = 750
                        }
                    val bundle = bundleOf("selectedWord" to word)
                    sharedViewModel.setDictionaryWord(true)
                    findNavController().navigate(
                        R.id.dictionary_bottom_nav,
                        bundle,

                        //set home screen to top of backStack to keep
                        //consistent user navigation (and not break bottomNav)
                        NavOptions.Builder().apply {
                            setPopUpTo(R.id.home_bottom_nav, false)
                        }.build()
                    )
                }

                override fun updateDrawState(ds: TextPaint) {
                    //set style of clickable words to bolded with the secondary color
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                    ds.isFakeBoldText = true
                }
            }

            //find words that are in the dictionary in the info section
            if (storyArticleSpan.contains(word)) {
                val first = storyArticleSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = storyArticleSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                storyArticleSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //find words that are in the dictionary in the info section
            if (habitatArticleSpan.contains(word)) {
                val first = habitatArticleSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = habitatArticleSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                habitatArticleSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //find words that are in the dictionary in the info section
            if (evolutionArticleSpan.contains(word)) {
                val first = evolutionArticleSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = evolutionArticleSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                evolutionArticleSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            //find words that are in the dictionary in the info section
            if (fossilArticleSpan.contains(word)) {
                val first = fossilArticleSpan.indexOf(word)
                var whileLoop = true
                var iterator = 0
                while (whileLoop) {
                    val newWord = fossilArticleSpan[first + iterator].toString()
                    if (newWord == " " || newWord == "." || newWord == ","
                        || newWord == "!" || newWord == "?"
                    ) {
                        whileLoop = false
                    } else {
                        iterator++
                    }
                }
                fossilArticleSpan.setSpan(
                    clickableSpan,
                    first,
                    first + iterator,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        with(binding) {
            storyText.setText(storyArticleSpan, TextView.BufferType.SPANNABLE)
            habitatText.setText(habitatArticleSpan, TextView.BufferType.SPANNABLE)
            crazyEvolutionText.setText(evolutionArticleSpan, TextView.BufferType.SPANNABLE)
            fossilHistoryText.setText(fossilArticleSpan, TextView.BufferType.SPANNABLE)
        }

        //Disables transitions when in the active state to avoid glitch
        with(binding) {
            habitatConstraintLayout.setTransitionListeners(habitatMotionLayout, 0)
            evolutionConstraintLayout.setTransitionListeners(evolutionMotionLayout, 1)
            fossilConstraintLayout.setTransitionListeners(fossilMotionLayout, 2)
        }

        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING

        //create list of all audios used for reading sections
        mediaPlayerList = createMediaPlayers()

        /*mediaPlayerList[0].setTimedText(binding.storyText.text as Spannable, storyArticle)
        mediaPlayerList[1].setTimedText(binding.habitatText.text as Spannable, habitatArticle)
        mediaPlayerList[2].setTimedText(binding.crazyEvolutionText.text as Spannable, evolutionArticle)
        mediaPlayerList[3].setTimedText(binding.fossilHistoryText.text as Spannable, fossilArticle)*/

        binding.activateAudioStory.setOnClickListener {
            activateAudioAnimation(viewModel.currentAudio, 0)
            activateAudio(
                viewModel.currentAudio,
                0,
            )
        }

        binding.activateAudioHabitat.setOnClickListener {
            activateAudioAnimation(viewModel.currentAudio, 1)
            activateAudio(
                viewModel.currentAudio,
                1,
            )
        }

        binding.activateAudioEvolution.setOnClickListener {
            activateAudioAnimation(viewModel.currentAudio, 2)
            activateAudio(
                viewModel.currentAudio,
                2,
            )
        }

        binding.activateAudioFossil.setOnClickListener {
            activateAudioAnimation(viewModel.currentAudio, 3)
            activateAudio(
                viewModel.currentAudio,
                3,
            )
        }

        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING
        //DEVELOPING

        sharedViewModel.quizVisible.observe(viewLifecycleOwner) {
            if (viewModel.initialized) {
                displayQuiz(it, quizText)
            } else {
                viewModel.setInitialized()
            }

            //enables/disables up button if quiz is open or not
            (activity as MainActivity).binding.activityToolbar.navigationIcon = if (it) {
                null
            } else {
                AppCompatResources.getDrawable(requireContext(), R.drawable.back_button)
            }
        }

        viewModel.nextButtonEnabled.observe(viewLifecycleOwner) {
            with(binding.quizMotionLayout) {
                setTransition(R.id.quiz_next_question)
                if (it) {
                    transitionToEnd()
                } else {
                    transitionToStart()
                }
            }
        }

        viewModel.nextButtonClicked.observe(viewLifecycleOwner) {

            //make sure quiz is visible or main thread is stalled continuously
            when (it) {
                0 -> return@observe
                null -> {
                    viewModel.resetAnswersCorrect()
                    binding.quizRadioGroup.clearCheck()
                    binding.quizMotionLayout.setTransition(R.id.retry)
                    return@observe
                }
            }

            val correctAnswer = quizCorrectAnswers[it - 1]
            when (viewModel.radioButtonClicked.value) {
                0 -> {
                    if (binding.quizRadioButton0.text == correctAnswer)
                        viewModel.updateAnswerCorrect()
                }
                1 -> {
                    if (binding.quizRadioButton1.text == correctAnswer)
                        viewModel.updateAnswerCorrect()
                }
                2 -> {
                    if (binding.quizRadioButton2.text == correctAnswer)
                        viewModel.updateAnswerCorrect()
                }
                3 -> {
                    if (binding.quizRadioButton3.text == correctAnswer)
                        viewModel.updateAnswerCorrect()
                }
            }

            viewModel.setRadioButtonClicked(binding.quizRadioGroup.checkedRadioButtonId)
            binding.quizRadioGroup.clearCheck()

            //only set the text for quiz for how many questions there are
            if (it < 5) {

                //set text in quiz with dataBinding
                viewModel.setQuizStringAnswers(
                    listOf(
                        quizText[it]?.get(0) ?: "ERROR",
                        quizText[it]?.get(1) ?: "ERROR",
                        quizText[it]?.get(2) ?: "ERROR",
                        quizText[it]?.get(3) ?: "ERROR",
                        quizText[it]?.get(4) ?: "ERROR"
                    )
                )
            } else {
                if (dinoSelected.activated || viewModel.quizPassed) {
                    with(binding.quizMotionLayout) {
                        setTransition(R.id.quiz_passed)
                        transitionToEnd()
                    }
                } else {
                    with(binding.quizMotionLayout) {
                        setTransition(R.id.result_transition)
                        transitionToEnd {
                            setTransition(R.id.result_resize_transition)
                            transitionToEnd()
                        }
                    }

                    if (viewModel.answersCorrect > 3) {
                        sharedViewModel.updateActivated(
                            dinoPosition,
                            true
                        )
                        viewModel.setQuizPassed()
                    }
                }
            }
        }

        viewModel.habitatDroppedDown.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    habitatMotionLayout.transitionToEnd()
                    habitatDropButton.setImageResource(R.drawable.drop_down_arrow_down)
                }
            } else {
                with(binding) {
                    habitatMotionLayout.transitionToStart()
                    habitatDropButton.setImageResource(R.drawable.drop_down_arrow_up)
                }
            }

            (binding.habitatDropButton.drawable as AnimatedVectorDrawable).start()
        }

        viewModel.evolutionDroppedDown.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    evolutionMotionLayout.transitionToEnd()
                    crazyEvolutionDropButton.setImageResource(R.drawable.drop_down_arrow_down)
                }
            } else {
                with(binding) {
                    evolutionMotionLayout.transitionToStart()
                    crazyEvolutionDropButton.setImageResource(R.drawable.drop_down_arrow_up)
                }
            }

            (binding.crazyEvolutionDropButton.drawable as AnimatedVectorDrawable).start()
        }

        viewModel.fossilDroppedDown.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    fossilMotionLayout.transitionToEnd()
                    fossilHistoryDropButton.setImageResource(R.drawable.drop_down_arrow_down)
                }
            } else {
                with(binding) {
                    fossilMotionLayout.transitionToStart()
                    fossilHistoryDropButton.setImageResource(R.drawable.drop_down_arrow_up)
                }
            }

            (binding.fossilHistoryDropButton.drawable as AnimatedVectorDrawable).start()
        }

        //sets text in article
        with(binding) {
            storyText.movementMethod =
                LinkMovementMethod.getInstance()

            livedFactText.text = livedFact
            speedFactText.text = speedFact
            nameFactText.text = nameMeaningFact
        }

        return binding.root
    }

    private fun displayQuiz(visible: Boolean, quizStrings: HashMap<Int, List<String>>) {

        viewModel.setQuizStringAnswers(
            listOf(
                quizStrings[0]?.get(0) ?: "ERROR",
                quizStrings[0]?.get(1) ?: "ERROR",
                quizStrings[0]?.get(2) ?: "ERROR",
                quizStrings[0]?.get(3) ?: "ERROR",
                quizStrings[0]?.get(4) ?: "ERROR"
            )
        )

        if (visible) {

            //disable audio if playing from a reading section
            viewModel.setCurrentAudio(-1)

            ObjectAnimator.ofFloat(
                binding.quizCardLayout, "scaleX", 0f
            ).apply {
                duration = 0
                start()
            }

            ObjectAnimator.ofFloat(
                binding.quizCardLayout, "scaleY", 0f
            ).apply {
                duration = 0
                start()
            }

            binding.quizCardLayout.visibility = View.VISIBLE
        }

        val displayInfo = ObjectAnimator.ofFloat(
            binding.constraintArticleLayout, "alpha", if (visible) 0f else 1f
        )

        val displayQuizX = ObjectAnimator.ofFloat(
            binding.quizCardLayout, "scaleX", if (visible) 1f else 0f
        )

        val displayQuizY = ObjectAnimator.ofFloat(
            binding.quizCardLayout, "scaleY", if (visible) 1f else 0f
        )

        //NOTE: PLAYS WHEN APP FIRST ENTERS
        AnimatorSet().apply {
            playTogether(listOf(displayInfo, displayQuizX, displayQuizY))
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()

            if (visible) {
                doOnStart {
                    if (dinoSelected.activated || viewModel.quizPassed) {
                        // display quiz already passed screen
                        viewModel.nextButtonClicked(5)
                    }
                    ViewCompat.setNestedScrollingEnabled(binding.scrollArticleLayout, false)
                    binding.scrollArticleLayout.isNestedScrollingEnabled = false
                }
                doOnEnd {
                    binding.backgroundMask.visibility = View.VISIBLE
                    binding.scrollArticleLayout.scrollTo(0, 0)
                }
            } else {
                doOnStart {
                    ViewCompat.setNestedScrollingEnabled(binding.scrollArticleLayout, true)
                    binding.scrollArticleLayout.isNestedScrollingEnabled = true
                    binding.backgroundMask.visibility = View.GONE
                }
                doOnEnd {
                    binding.quizCardLayout.visibility = View.GONE
                    viewModel.nextButtonClicked(0)
                }
            }
            start()
        }
        //NOTE: PLAYS WHEN APP FIRST ENTERS
    }

    /** Disables transitions when in the active state **/
    private fun ConstraintLayout.setTransitionListeners(motionLayout: MotionLayout, layout: Int) {
        motionLayout.setTransitionListener(
            object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                    //disables spannable text clicks when animating to prevent scroll glitch
                    when (layout) {
                        0 -> binding.habitatText.movementMethod = null
                        1 -> binding.crazyEvolutionText.movementMethod = null
                        2 -> binding.fossilHistoryText.movementMethod = null
                    }
                    isClickable = false
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    when (layout) {
                        0 -> binding.habitatText.movementMethod =
                            LinkMovementMethod.getInstance()
                        1 -> binding.crazyEvolutionText.movementMethod =
                            LinkMovementMethod.getInstance()
                        2 -> binding.fossilHistoryText.movementMethod =
                            LinkMovementMethod.getInstance()
                    }
                    isClickable = true
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                }
            }
        )
    }

    /** Creates 4 MediaPlayers for each reading section **/
    private fun createMediaPlayers(): List<MediaPlayer> {
        val mediaPlayers = mutableListOf<MediaPlayer>()

        for(i in 0..3) {
            val mediaPlayer = MediaPlayer()
            val assetFile = resources.openRawResourceFd(dinoSelected.getDinosaurAudio(i))
            mediaPlayer.setDataSource(assetFile)
            assetFile.close()
            mediaPlayers.add(mediaPlayer)

            mediaPlayer.apply {
                isLooping = false
                setOnPreparedListener {
                    it.start()
                }
                createTimedText(this, i)
            }
        }

        return mediaPlayers
    }

    /** Play audio of a reading section and stop audio of previous reading section **/
    private fun activateAudio(
        currentAudio: Int?,
        newAudio: Int,
    ) {
        val audio = if (currentAudio == -1) {
            mediaPlayerList[newAudio]
        } else {
            mediaPlayerList[currentAudio ?: newAudio]
        }

        //if the previous audio is still playing stop it
        if (audio.isPlaying) {
            audio.stop()
        }

        if (currentAudio == newAudio) {
            viewModel.setCurrentAudio(-1)
        } else {
            viewModel.setCurrentAudio(newAudio)
            mediaPlayerList[newAudio].prepareAsync()
        }
    }

    /** animate audio icon to show audio is playing **/
    private fun activateAudioAnimation(previousAudio: Int?, currentAudio: Int): Boolean {

        val previousAudioAnimation: AnimationDrawable? = when(previousAudio) {
            0 -> storyAudioAnimation
            1 -> habitatAudioAnimation
            2 -> evolutionAudioAnimation
            3 -> fossilAudioAnimation
            else -> null
        }

        val currentAudioAnimation: AnimationDrawable = when(currentAudio) {
            0 -> storyAudioAnimation
            1 -> habitatAudioAnimation
            2 -> evolutionAudioAnimation
            3 -> fossilAudioAnimation
            else -> null
        } ?: return false

        if(currentAudioAnimation.isRunning) {
            currentAudioAnimation.apply {
                stop()
                selectDrawable(0)
            }
        } else {
            previousAudioAnimation?.apply {
                stop()
                selectDrawable(0)
            }
            currentAudioAnimation.start()
        }

        return true
    }

    /** stops any and all animated audio icons if playing **/
    private fun stopAudioAnimation(): Boolean {
        if(storyAudioAnimation.isRunning) {
            storyAudioAnimation.stop()
            return true
        }
        if(habitatAudioAnimation.isRunning) {
            habitatAudioAnimation.stop()
            return true
        }
        if(evolutionAudioAnimation.isRunning) {
            evolutionAudioAnimation.stop()
            return true
        }
        if(fossilAudioAnimation.isRunning) {
            fossilAudioAnimation.stop()
            return true
        }
        return false
    }

    /** create timedText for MediaPlayer to allow user to read along with audio **/
    @Suppress("DEPRECATION")
    private fun createTimedText(mediaPlayer: MediaPlayer, section: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mediaPlayer.addTimedTextSource(
                getSubtitleFile(section),
                MediaFormat.MIMETYPE_TEXT_SUBRIP
            )
        } else {
            mediaPlayer.addTimedTextSource(
                getSubtitleFile(section),
                MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP
            )
        }

        val textTrackIndex = findTrackIndexFor(
            TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, mediaPlayer.trackInfo
        )

        if (textTrackIndex >= 0) {
            mediaPlayer.selectTrack(textTrackIndex)
        }
    }

    private fun MediaPlayer.setTimedText(spannable: Spannable, article: String) {

        Log.i(TAG, "Spannable: $spannable")
        Log.i(TAG, "Article: $article")

        var subtitleStoryProgress = 0
        var lastSpan: CharacterStyle? = null

        viewModel.resetSubtitles.observe(viewLifecycleOwner) {
            spannable.removeSpan(lastSpan)
            subtitleStoryProgress = 0
        }

        setOnTimedTextListener { _, timedText ->

            Log.i(TAG, "lastSpan: ${timedText.text}")

            val timedTextLength = timedText.text.length

            val styledSpan: CharacterStyle = object : android.text.style.CharacterStyle() {
                override fun updateDrawState(textPaint: TextPaint) {
                    textPaint.isFakeBoldText = true

                    val typedValue = object : TypedValue() {}
                    requireActivity().theme.resolveAttribute(R.attr.colorSecondary, typedValue, true)
                    textPaint.color = typedValue.data
                }

            }

            if(subtitleStoryProgress == 0) {
                var matchedLength = 0

                var index = 0
                while(matchedLength != timedTextLength - 1 && matchedLength < timedTextLength) {

                    if(timedText.text[matchedLength] == article[index]) {
                        matchedLength++
                    }

                    index++
                }

                spannable.setSpan(
                    styledSpan,
                    0,
                    index,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                lastSpan = styledSpan
                subtitleStoryProgress += index

                return@setOnTimedTextListener
            }

            spannable.removeSpan(lastSpan)


            spannable.setSpan(
                styledSpan,
                subtitleStoryProgress,
                subtitleStoryProgress + timedTextLength,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            lastSpan = styledSpan
            subtitleStoryProgress += timedTextLength
        }
    }

    private fun stopMediaPlayers(release: Boolean) {
        if(release) {
            for(i in mediaPlayerList) {
                if(i.isPlaying) {
                    i.release()
                }
            }
        } else {
            for(i in mediaPlayerList) {
                if(i.isPlaying) {
                    viewModel.setCurrentAudio(-1)
                    i.stop()
                }
            }
        }
    }

    private fun findTrackIndexFor(mediaTrackType: Int, trackInfo: Array<TrackInfo>): Int {
        val index = -1
        for (i in trackInfo.indices) {
            if (trackInfo[i].trackType == mediaTrackType) {
                return i
            }
        }
        return index
    }

    private fun getSubtitleFile(section: Int): String {
        val rawResource = dinoSelected.getDinosaurSubtitle(section)
        val fileName = resources.getResourceEntryName(rawResource)
        Log.i(TAG, "Subtitle File: $fileName")
        val subtitleFile: File = requireActivity().getFileStreamPath(fileName)
        if (subtitleFile.exists()) {
            Log.i(TAG, "Subtitle File Exists: ${subtitleFile.absoluteFile.readText()}")
            return subtitleFile.absolutePath
        }

        // Copy the file from the res/raw folder to your app folder on the
        // device
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            inputStream = resources.openRawResource(rawResource)
            outputStream = FileOutputStream(subtitleFile, false)
            copyFile(inputStream, outputStream)
            return subtitleFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            closeStreams(inputStream, outputStream)
        }
        return ""
    }

    @Throws(IOException::class)
    private fun copyFile(inputStream: InputStream, outputStream: OutputStream?) {
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var length: Int
        while (inputStream.read(buffer).also { length = it } != -1) {
            outputStream!!.write(buffer, 0, length)
        }
    }

    // A handy method I use to close all the streams
    private fun closeStreams(vararg closeables: Closeable?) {
        for (stream in closeables) {
            try {
                stream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onPause() {
        stopMediaPlayers(false)
        stopAudioAnimation()
        super.onPause()
    }

    override fun onDestroyView() {
        stopMediaPlayers(true)
        stopAudioAnimation()
        super.onDestroyView()
    }
}