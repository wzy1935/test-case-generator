# test-case-generator
这是一个临时的文档，很多内容都没有完善。如果您需要测试本程序，可前往/src/project/file/目录下寻找input.txt文件，将样例生成语言的代码写入后，程序将在output.txt下生成您所需要的样例。

下面是样例生成语言的一篇临时入门文档。您需要使用它来编写代码。不过不要担心，这门语言非常简单。着急的话，您可以先前往1.7节了解一些示例。

# Document

## 1 开始使用

***
### 1.1 字段

**字段（field）分为三类，词段（word），句段（line），和区段（para）。 命名一个字段的方法如下:**

    field restriction name statement

- field表示字段类型，可使用word/line/para关键字来定义；
- restriction表示限制关键字，可不填，包括final/set
- name表示你为此字段定义的名称
- statement为字段的具体内容

下面将对上述三类字段详细介绍：

***
### 1.2 词段 word

词段表示一个简单的词，可以表示整数，浮点数，和字符串。词段的内容以小括号()包括。词段具有两种声明方式。

**对所有类型，可使用如下的声明方式：**

    (A, B, C, D, ...)
    (A B C D ...)

运行时，将取括号内的任意一个值作为返回值。值与值之间使用逗号或空格隔开。

整数声明直接将待选数枚举即可。下方为产生1~5之间随机整数的实例：

    (1, 2, 3, 4, 5)

浮点数声明需要标记小数点。下方为产生随机浮点数的实例：

    (1.0, 1.32, 4.5, 7.8)

字符串声明需要使用双引号包含。下方为生成随机姓名的实例：

    ("Amy", "Bertha", "Clare")

**对于整数、浮点数，还可以使用区间声明。声明方式如下：**

    (range1 ~ range2)

range1和range2分别表示下界和上界。运行时，将产生一个大于等于range1且小于等于range2之间的值。下方为产生1~100之间随机整数的实例：

    (1 ~ 100)

词段可以直接作为语句来使用，当然，也可以对词段命名方便后续使用。下方为一个词段命名的实例：

    word n (0.0~100.0)

上述语句定义了一个词段n，表示0~100之间的浮点数。

***
### 1.3 句段 line

句段表示完整的一行，由若干个词段组成。句段的内容以中括号[]包括。**其声明方式如下：**

    [word1 word2 word3 ...]

词段之间可以使用若干空格与回车隔开。下方为一些句段生成的实例：

    word n (1~5)
    [n n n n n]

上述代码将生成一行包括五个随机数的句段，随机数范围为1~5。

    word x ("Amy" "Tom")
    [x ("love" "hate") ("programming.")]

上述代码将输出"Amy/Tom love/hate programming."。

句段也可以使用line关键字命名。下方为一个句段命名的实例：

    line s [(1~3) (4~6)]

上述语句定义了一个句段s，其中包含两个数，范围分别为1\~3，4\~6。

***
### 1.4 区段 para

区段表示一个完整的段落，由若干个句段和区段组成。区段的内容以大括号{}包括。**其声明方式如下：**

    {line1/para1 line2/para2 line3/para3 ...}

区段句段之间可以使用若干空格与回车隔开。下方为一些区段生成的实例：

    {
        word num (1~5)
        [(1~10)]
        [num num num]
        [num num num]
    }

上述区段包括三行，第一行为1\~10之间的随机数，第二三行分别包括三个1\~5之间的随机数。

需要注意的是，**命名语句能够写在括号内。当其出现在括号中时不会返回值。**

    {
        {
            [(1~10)]
            [
                word n (0,1)
                n n n n n
            ]
        }

        [(1~100)]
    }

上述区段包括三行，第一行为1\~10之间随机数，第二行为五个0或1，第三行为1\~100之间的随机数。

区段命名使用para关键字。以下为使用实例：

    para case {
        [(1~10)]
        [("Y","N")]
    }

    {
        case
        [(1~5) (1~5)]
    }

上述区段将返回三行。前两行为case内容，分别为1~10随机数，字符"Y"/"N"。第三行包含两个1~5之间的随机数。

**一个完整的样例生成程序将包括一个main区段。程序执行时将返回main区段所返回的值。**

***
### 1.5 final关键字

final关键字为命名字段时使用的限制关键字。**当对一个字段声明final后，在该字段第一次生成返回值以后其值将不会再改变。** 以下为使用实例：

    para main {
        word final number (1~100)
        [number number number]
        [(1~number)]
    }

上述程序将先生成一个1~100之间的数，将其命名为number，再返回两行内容，第一行返回三个相同的number值，第二行返回一个1\~number之间的值。可能的一个输出如下：

    59 59 59
    34

若不对number使用final关键字，则number就代表1~100间的随机数，第一行的返回值也可能不同。可能的一个输出如下：

    1 97 36
    42

final关键字只能在命名语句中使用，无法对未命名的字段进行限制。

***
### 1.6 字段重复

**可以使用*运算符对字段进行重复操作。语法如下：**

    field * times

field表示待重复的字段，times表示需要重复的次数。实例如下：

    para main {
        word final T (1~10)
        [(1~100) *2] *T
    }

程序将输出T行，T为1\~10之间的随机数，每行都包括两个1\~100间的数。

使用*运算符时需注意：

- 生成的新内容和之前的内容是否相同取决于该内容是否被final修饰；
- 重复次数可以是命名过的词段，也可以是随机数，但要求必须是正整数；
- 对词段做重复操作时，新生成的内容将出现在同一行，而对句段或区段操作时，新内容将生成在新的列中。

***
### 1.7 实例演示

下面将演示如何使用样例生成程序对实际问题编写程序。

题干：

<i>The first line of input contains an integer T(1<=T<=10) which is the total number of test cases.

For each test case, there are two lines.

The first line contains a integer n, n<=100000.

The second line contains n integers, representing the sequence {a[i]} (1<=ai<=100000)</i>

据此所编写的测试代码如下：

    para main {
        word final T (1~10)
        para case {
            word final n (1~100000)
            [n]
            [(1~100000)*n]
        }

        [T]
        case*T
    }

题干：

<i>The first line contains two integers n and m (1 ≤ m ≤ n ≤ 300000).

The second line contains n integers a1, a2, ..., an (|ai| ≤ 1000000).</i>

据此所编写的测试代码如下：

    para main {
        word final n (1~300000)
        word final m (1~n)

        [n m]
        [(-1000000~1000000)*n]
    }