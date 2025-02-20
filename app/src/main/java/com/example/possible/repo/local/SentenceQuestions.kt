package com.example.possible.repo.local

import com.example.possible.model.SentenceCombining
import com.example.possible.model.SentenceCompleting

object SentenceQuestions {

    private val sentenceCombiningQuestions = listOf(
        SentenceCombining(
            "The quick brown fox",
            "jumps over the lazy dog",
            "The quick brown fox jumps over the lazy dog"
        ),
        SentenceCombining(
            "She sells seashells",
            "by the seashore",
            "She sells seashells by the seashore"
        ),
        SentenceCombining(
            "I think, therefore",
            "I am",
            "I think, therefore I am"
        ),
        SentenceCombining(
            "To be or not to be",
            "that is the question",
            "To be or not to be that is the question"
        ),
        SentenceCombining(
            "All that glitters",
            "is not gold",
            "All that glitters is not gold"
        ),
        SentenceCombining(
            "A journey of a thousand miles",
            "begins with a single step",
            "A journey of a thousand miles begins with a single step"
        ),
        SentenceCombining(
            "Ask not what your country can do for you",
            "ask what you can do for your country",
            "Ask not what your country can do for you ask what you can do for your country"
        ),
        SentenceCombining(
            "The only thing we have to fear",
            "is fear itself",
            "The only thing we have to fear is fear itself"
        ),
        SentenceCombining(
            "That's one small step for man",
            "one giant leap for mankind",
            "That's one small step for man one giant leap for mankind"
        ),
        SentenceCombining(
            "In the beginning",
            "God created the heavens and the earth",
            "In the beginning God created the heavens and the earth"
        ),
        SentenceCombining(
            "The early bird",
            "catches the worm",
            "The early bird catches the worm"
        ),
        SentenceCombining(
            "A picture is worth",
            "a thousand words",
            "A picture is worth a thousand words"
        ),
        SentenceCombining(
            "When in Rome",
            "do as the Romans do",
            "When in Rome do as the Romans do"
        ),
        SentenceCombining(
            "The pen is mightier",
            "than the sword",
            "The pen is mightier than the sword"
        ),
        SentenceCombining(
            "Actions speak",
            "louder than words",
            "Actions speak louder than words"
        ),
        SentenceCombining(
            "Beauty is in the eye",
            "of the beholder",
            "Beauty is in the eye of the beholder"
        ),
        SentenceCombining(
            "Necessity is the mother",
            "of invention",
            "Necessity is the mother of invention"
        ),
        SentenceCombining(
            "Fortune favors",
            "the bold",
            "Fortune favors the bold"
        ),
        SentenceCombining(
            "Birds of a feather",
            "flock together",
            "Birds of a feather flock together"
        ),
        SentenceCombining(
            "A watched pot",
            "never boils",
            "A watched pot never boils"
        ),
        SentenceCombining(
            "Beggars can't",
            "be choosers",
            "Beggars can't be choosers"
        ),
        SentenceCombining(
            "Better late",
            "than never",
            "Better late than never"
        ),
        SentenceCombining(
            "Two heads are",
            "better than one",
            "Two heads are better than one"
        ),
        SentenceCombining(
            "The grass is always greener",
            "on the other side",
            "The grass is always greener on the other side"
        ),
        SentenceCombining(
            "Don't count your chickens",
            "before they hatch",
            "Don't count your chickens before they hatch"
        ),
        SentenceCombining(
            "You can't judge a book",
            "by its cover",
            "You can't judge a book by its cover"
        ),
        SentenceCombining(
            "A penny saved",
            "is a penny earned",
            "A penny saved is a penny earned"
        ),
        SentenceCombining(
            "The squeaky wheel",
            "gets the grease",
            "The squeaky wheel gets the grease"
        ),
        SentenceCombining(
            "When the going gets tough",
            "the tough get going",
            "When the going gets tough the tough get going"
        ),
        SentenceCombining(
            "No man is",
            "an island",
            "No man is an island"
        ),
        SentenceCombining(
            "You can't have your cake",
            "and eat it too",
            "You can't have your cake and eat it too"
        ),
        SentenceCombining(
            "There's no place",
            "like home",
            "There's no place like home"
        ),
        SentenceCombining(
            "The best things in life",
            "are free",
            "The best things in life are free"
        ),
        SentenceCombining(
            "Time heals",
            "all wounds",
            "Time heals all wounds"
        ),
        SentenceCombining(
            "Practice makes",
            "perfect",
            "Practice makes perfect"
        ),
        SentenceCombining(
            "Too many cooks",
            "spoil the broth",
            "Too many cooks spoil the broth"
        ),
        SentenceCombining(
            "Many hands make",
            "light work",
            "Many hands make light work"
        ),
        SentenceCombining(
            "Absence makes the heart",
            "grow fonder",
            "Absence makes the heart grow fonder"
        ),
        SentenceCombining(
            "Out of sight",
            "out of mind",
            "Out of sight out of mind"
        ),
        SentenceCombining(
            "You can't teach an old dog",
            "new tricks",
            "You can't teach an old dog new tricks"
        ),
        SentenceCombining(
            "A rolling stone",
            "gathers no moss",
            "A rolling stone gathers no moss"
        ),
        SentenceCombining(
            "Still waters",
            "run deep",
            "Still waters run deep"
        ),
        SentenceCombining(
            "The apple doesn't fall far",
            "from the tree",
            "The apple doesn't fall far from the tree"
        ),
        SentenceCombining(
            "Don't put all your eggs",
            "in one basket",
            "Don't put all your eggs in one basket"
        ),
        SentenceCombining(
            "Every cloud has",
            "a silver lining",
            "Every cloud has a silver lining"
        ),
        SentenceCombining(
            "A stitch in time",
            "saves nine",
            "A stitch in time saves nine"
        ),
        SentenceCombining(
            "An apple a day",
            "keeps the doctor away",
            "An apple a day keeps the doctor away"
        ))



    private val sentenceCompletingExamples = listOf(
        SentenceCompleting(
            firstSentence = "She wanted to go for a walk",
            secondSentence = "it was raining outside",
            theWordToAdd = "but",
            listToChooseFrom = listOf("and", "but", "because", "so")
        ),
        SentenceCompleting(
            firstSentence = "He studied hard for the exam",
            secondSentence = "he could achieve a high score",
            theWordToAdd = "so",
            listToChooseFrom = listOf("and", "but", "so", "because")
        ),
        SentenceCompleting(
            firstSentence = "They decided to stay indoors",
            secondSentence = "the heavy snowfall",
            theWordToAdd = "because of",
            listToChooseFrom = listOf("in spite of", "because of", "and", "but")
        ),
        SentenceCompleting(
            firstSentence = "The team played well",
            secondSentence = "they lost the match",
            theWordToAdd = "yet",
            listToChooseFrom = listOf("so", "because", "yet", "and")
        ),
        SentenceCompleting(
            firstSentence = "She enjoys reading books",
            secondSentence = "novels and biographies",
            theWordToAdd = "especially",
            listToChooseFrom = listOf("but", "especially", "so", "and")
        ),
        SentenceCompleting(
            firstSentence = "He went to the store",
            secondSentence = "buy some groceries",
            theWordToAdd = "to",
            listToChooseFrom = listOf("but", "to", "and", "so")
        ),
        SentenceCompleting(
            firstSentence = "The project was challenging",
            secondSentence = "we completed it on time",
            theWordToAdd = "nevertheless",
            listToChooseFrom = listOf("because", "so", "nevertheless", "and")
        ),
        SentenceCompleting(
            firstSentence = "She practices the piano daily",
            secondSentence = "improve her skills",
            theWordToAdd = "in order to",
            listToChooseFrom = listOf("but", "in order to", "and", "so")
        ),
        SentenceCompleting(
            firstSentence = "The weather was perfect",
            secondSentence = "we went for a hike",
            theWordToAdd = "so",
            listToChooseFrom = listOf("but", "because", "so", "and")
        ),
        SentenceCompleting(
            firstSentence = "He was tired",
            secondSentence = "he continued working",
            theWordToAdd = "but",
            listToChooseFrom = listOf("so", "because", "but", "and")
        ),
        // Additional examples
        SentenceCompleting(
            firstSentence = "The sun was setting",
            secondSentence = "the sky turned orange",
            theWordToAdd = "as",
            listToChooseFrom = listOf("but", "as", "so", "because")
        ),
        SentenceCompleting(
            firstSentence = "She didn't see the step",
            secondSentence = "she tripped and fell",
            theWordToAdd = "therefore",
            listToChooseFrom = listOf("and", "but", "therefore", "so")
        ),
        SentenceCompleting(
            firstSentence = "He loves to travel",
            secondSentence = "he rarely has time for vacations",
            theWordToAdd = "yet",
            listToChooseFrom = listOf("so", "because", "yet", "and")
        ),
        SentenceCompleting(
            firstSentence = "The cake was burnt",
            secondSentence = "she forgot to set a timer",
            theWordToAdd = "because",
            listToChooseFrom = listOf("but", "because", "so", "and")
        ),
        SentenceCompleting(
            firstSentence = "They saved money for months",
            secondSentence = "they could buy a new car",
            theWordToAdd = "so that",
            listToChooseFrom = listOf("but", "because", "so that", "and")
        ),
        SentenceCompleting(
            firstSentence = "The movie was boring",
            secondSentence = "we left before it ended",
            theWordToAdd = "so",
            listToChooseFrom = listOf("but", "because", "so", "and")
        ),
        SentenceCompleting(
            firstSentence = "He didn't study",
            secondSentence = "he failed the exam",
            theWordToAdd = "consequently",
            listToChooseFrom = listOf("but", "because", "consequently", "and")
        ),
        SentenceCompleting(
            firstSentence = "She was very tired",
            secondSentence = "she kept working",
            theWordToAdd = "nevertheless",
            listToChooseFrom = listOf("but", "because", "nevertheless", "and")
        ),
        SentenceCompleting(
            firstSentence = "The store was closed",
            secondSentence = "we went to another one",
            theWordToAdd = "so",
            listToChooseFrom = listOf("but", "because", "so", "and")
        ),
        SentenceCompleting(
            firstSentence = "He didn't like the meal",
            secondSentence = "he ate it anyway",
            theWordToAdd = "but",
            listToChooseFrom = listOf("but", "because", "so", "and")
        )
    )


    fun getRandomCombingSentence(): SentenceCombining{
        return sentenceCombiningQuestions.random()
    }
    fun getRandomCompletingSentence(): SentenceCompleting{
        return sentenceCompletingExamples.random()
    }

}