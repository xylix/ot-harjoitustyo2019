package kaantelypeli.utils

/**
 * Miscellaneous parsing utilities.
 */
class Parsing private constructor() {
    companion object {
        /**
         * Removes prettiness from Json String.
         * @param input JSON string
         * @return input stripped from newlines and spaces
         */
        fun uglify(input: String): String {
            return input.replace(System.lineSeparator(), "").replace(" ", "")
        }

        /**
         * Parse String to Integer with default value if String isn't a valid Integer.
         * @param s String to parse
         * @param defaultValue int value to default to
         * @return Parsed Integer or the default
         */
        @JvmStatic
        fun valueOfWithDefault(s: String, defaultValue: Int): Int {
            return if (s.matches("-?\\d+".toRegex())) s.toInt() else defaultValue
        }
    }

    init {
        throw IllegalStateException("Utility class")
    }
}
