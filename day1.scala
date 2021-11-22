import scala.annotation.tailrec

object gen {
  def main(args: Array[String]): Unit = {
    println(hello(1)("hello"))
    var n = 10
    myWhile3(n >= 0) {
      println(n)
      n -= 1;
    }
    println("====================")
    var fun = addFactory(9)
    println(fun(1))
    println(fun(2))
    println("====================")
    var myminus = opsFactory(12, (i, j) => i - j)
    println(myminus(1))
    println(myminus(2))
    println("====================")
    parameterByName{
      println("when evaluated")
      println("Name")
    }
    println("====================")
    parameterByValue{
        println("when evaluated")
        ()=>println("Value")
    }
  }

  def hello(i: Int)(j: String): Int = {
    val res: Int = j.indexOf('o') + i
    res
  }
  //根据传入的参数初始化，并且进行累加 闭包
  def addFactory(initial: Int): Int => Int = {
    var inner: Int = initial
    (i) => {
      inner = inner + i
      inner
    }
  }

  //根据传入的参数给定初始值，通过传入的操作ops进行操作
  def opsFactory(initial: Int, ops: (Int, Int) => Int): Int => Int = {
    var inner: Int = initial
    (i) => {
      inner = ops(inner, i)
      inner
    }
  }

  //实现while循坏 传名参数
  def myWhile(condition: => Boolean): (=> Unit) => Unit = {
    def loop(op: => Unit): Unit = {
      if (condition) {
        op
        myWhile(condition)(op)
      }
    }

    loop
  }

  //简化了里面的返回 传名参数
  def myWhile2(condition: => Boolean): (=> Unit) => Unit = {
    (op) => {
      if (condition) {
        op
        myWhile2(condition)(op)
      }
    }
  }


  //柯西化
  @tailrec //尾递归优化
  def myWhile3(condition: => Boolean)(ops: => Unit): Unit = {
    if (condition) {
      ops
      myWhile3(condition)(ops)
    }
  }

  def parameterByName(code: =>Unit): Unit ={
    println("start-N")
    code
    println("end-N")
  }
  def parameterByValue(code: ()=>Unit): Unit ={
    println("start-V")
    //与上面的区别在于，需要添加括号
    code()
    println("end-V")
  }
}
