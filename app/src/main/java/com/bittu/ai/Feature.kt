package com.bittu.ai

data class Feature(
    val title: String,
    val description: String,
    val category: String
)

object FeatureCatalog {
    val all = listOf(
        Feature("AI Chat", "सवाल पूछें, ज्ञान पाएं, विचार और विश्लेषण करें।", "Knowledge"),
        Feature("Coding", "Python, Kotlin, Java, C++, JavaScript, SQL और अन्य भाषाओं में कोड बनवाएं।", "Creation"),
        Feature("Android App", "Android application का design, code और APK build plan बनवाएं।", "Creation"),
        Feature("Website", "Website, web app, frontend और backend बनवाएं।", "Creation"),
        Feature("Database", "SQL, database design, API और backend बनवाएं।", "Creation"),
        Feature("Login System", "Login, signup, authentication और security बनवाएं।", "Creation"),
        Feature("Payment", "Payment integration और billing system का plan बनवाएं।", "Creation"),
        Feature("AI Chatbot", "AI chatbot, RAG, memory और tool-calling system बनवाएं।", "AI"),
        Feature("AI Development", "Neural network, transformer, tokenizer, fine-tuning और model evaluation।", "AI"),
        Feature("Research", "Deep research, sources, fact-checking और report बनवाएं।", "Research"),
        Feature("Science", "Physics, chemistry, biology और scientific analysis।", "Research"),
        Feature("Mathematics", "Algebra, geometry, calculus, statistics और advanced mathematics।", "Research"),
        Feature("History", "History, geography और archaeology का अध्ययन करें।", "Knowledge"),
        Feature("Philosophy", "Philosophy, logic, arguments और contradictions analyze करें।", "Knowledge"),
        Feature("Computer Science", "Operating systems, compilers, algorithms और engineering।", "Knowledge"),
        Feature("Economics", "Economics, finance, business model और investment analysis।", "Business"),
        Feature("Law", "Legal information, contracts, complaints और RTI drafts।", "Legal"),
        Feature("Language", "Translation, grammar, pronunciation और language learning।", "Knowledge"),
        Feature("Space", "Astronomy, astrophysics, rockets और space missions।", "Research"),
        Feature("Psychology", "Psychology, sociology और human behaviour analysis।", "Knowledge"),
        Feature("Images", "Logo, poster, UI/UX, infographic और technical concept art।", "Creation"),
        Feature("Voice", "Voice input, speech-to-text, text-to-speech और voice conversation।", "Creation"),
        Feature("Documents", "PDF, Word, Excel, PowerPoint, CSV और reports बनाएं।", "Creation"),
        Feature("Data Analysis", "Excel, CSV, statistics, charts और stock market analysis।", "Business"),
        Feature("Business", "Business plan, market research, branding और startup strategy।", "Business"),
        Feature("Security", "Secure coding, vulnerability assessment, encryption और security audit।", "Security"),
        Feature("Hardware", "CPU, GPU, FPGA, Verilog, embedded systems और chip design।", "Hardware"),
        Feature("Robotics", "Robot control, sensors, computer vision, drones और automation।", "Hardware"),
        Feature("Blockchain", "Blockchain, smart contracts, wallets, tokens और security audit।", "Technology"),
        Feature("GitHub", "Repository, commits, pull requests, code review और deployment।", "Execution"),
        Feature("Agents", "Research, coding, testing, debugging, security और documentation agents।", "Autonomous"),
        Feature("Learning", "Personal tutor, quiz, flashcards, exam preparation और study plan।", "Learning"),
        Feature("Health", "Nutrition, fitness, sleep और general health information।", "Health"),
        Feature("Government", "Government schemes, documents, applications और procedures।", "Legal")
    )
}
