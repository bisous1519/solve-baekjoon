let N; // 수식의 길이
let Exp; // 수식배열
let Max = Number.MIN_SAFE_INTEGER;

const calcOne = (num1, oper, num2) => {
  switch (oper) {
    case '+':
      return num1 + num2;
    case '-':
      return num1 - num2;
    case '*':
      return num1 * num2;
  }
};

const calcArr = (exp) => {
  let answer = +exp[0];
  for (let i = 2; i < exp.length; i += 2) {
    answer = calcOne(answer, exp[i - 1], +exp[i]);
  }

  return answer;
};

const bracket = (exp, idx) => {
  // 주어진 식 계산
  if (idx >= exp.length - 1) {
    const calc = calcArr(exp);
    Max = Math.max(Max, calc);
    return;
  }

  // 괄호 안쳐서 넘기고
  bracket([...exp], idx + 2);

  // 괄호 쳐서(계산해서) 넘기고
  let newExp = [];
  let i = 0;
  while (i < exp.length) {
    if (i != idx) {
      newExp = [...newExp, exp[i]];
      i++;
    } else {
      newExp = [...newExp, calcOne(+exp[i], exp[i + 1], +exp[i + 2])];
      i += 3;
    }
  }
  bracket([...newExp], idx + 2);
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');
  N = +input[0];
  Exp = [...input[1].split('')];
  //입력

  bracket([...Exp], 0);

  console.log(Max);
};

main();