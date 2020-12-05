package org.junit.contrib.java.lang.system;

import org.junit.contrib.java.lang.system.internal.PrintStreamHandler.SYSTEM_ERR;

import org.junit.contrib.java.lang.system.internal.LogPrintStream;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

fun muteForSuccessfulTestsAndEnableLog() {
    val logPrintStream: LogPrintStream = LogPrintStream(SYSTEM_ERR);
    logPrintStream.muteForSuccessfulTests();
    logPrintStream.enableLog();
}

public class SystemErrRule: TestRule {
    private val logPrintStream: LogPrintStream = LogPrintStream(SYSTEM_ERR);

    
    fun mute(): SystemErrRule {
        logPrintStream.mute();
	return this;
    }


	fun muteForSuccessfulTests(): SystemErrRule {
		logPrintStream.muteForSuccessfulTests();
		return this;
	}

	fun clearLog() {
		logPrintStream.clearLog();
	}


	fun getLog(): String {
		return logPrintStream.getLog();
	}

	fun getLogWithNormalizedLineSeparator(): String {
		return logPrintStream.getLogWithNormalizedLineSeparator();
	}


	fun getLogAsBytes(): ByteArray  {
		return logPrintStream.getLogAsBytes();
	}

	fun enableLog(): SystemErrRule {
		logPrintStream.enableLog();
		return this;
	}
	
	override fun apply(base: Statement, description: Description): Statement {
		return logPrintStream.createStatement(base);
	}
}
