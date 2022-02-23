package kotlinlang.basics

object ClassesTest {
    fun run(){
        KtInheritanceTest.run()
    }
}

class KtInheritanceTest: SomeInterface, SomeOtherInterface, SomeSuperClass() {
    override val classTag = "this"
    private val classInfo = "what"
    override fun reportInterface(): String {
        return classTag
    }

    override fun reportOthers(): String {
        return classInfo
    }
    companion object {
        fun run(){
            KtInheritanceTest().let {
                println((it as? SomeSuperClass)?.classTag)
                println((it as? SomeInterface)?.reportInterface())
                println((it as? SomeOtherInterface)?.reportOthers())
            }
        }
    }
}

interface SomeInterface {
    fun reportInterface(): String
}

open class SomeSuperClass{
    open val classTag: String = "super"
}

interface SomeOtherInterface {
    fun reportOthers(): String
}