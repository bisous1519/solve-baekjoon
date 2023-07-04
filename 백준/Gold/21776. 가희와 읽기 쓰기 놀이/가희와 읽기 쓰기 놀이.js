let N; // 총 사람 수
let C; // 총 카드 수
let Order = []; // 카드내는 순서 들어있는 배열
let Cards = []; // 각 카드마다 쓰여있는 연산
let ArrangePersonNum = []; // 낼 카드 개수만큼 사람 번호 넣어놓는 배열
let ArrangeCardNum;
let CntCards = []; // 각 사람마다 갖고있는 카드 개수
let resultSet;
let answerSet = new Set();

const gameStart = () => {
  // ArrangeCardNum에 있는 순서대로 게임 진행
  let str = '';
  for (let i = 0; i < C; i++) {
    const curCard = ArrangeCardNum[i] - 1;
    for (const el of Cards[curCard]) {
      if ('a' <= el && el <= 'z') {
        str += el;
      } else {
        if (+el <= str.length - 1) {
          str = str.substring(0, +el) + str.substring(+el + 1);
        } else {
          answerSet.add('ERROR');
          return;
        }
      }
    }
  }
  if (str.length === 0) {
    answerSet.add('EMPTY');
  } else {
    answerSet.add(str);
  }
};

const makeCardOrder = () => {
  ArrangeCardNum = new Array(C);
  let nth = new Array(N).fill(0);

  for (let i = 0; i < C; i++) {
    const pNum = result[i] - 1;
    ArrangeCardNum[i] = Order[pNum][nth[pNum]++];
  }
  //   console.log(ArrangeCardNum);
};

const permu = (nth, isSelected, result) => {
  if (nth === C) {
    // 카드 내는 전체 순서가 정해짐 (result배열)
    if (!resultSet.has(result.toString())) {
      //   console.log(result, result.toString());
      resultSet.add(result.toString());
      makeCardOrder();
      gameStart();
    }
    return;
  }

  for (let i = 0; i < C; i++) {
    if (!isSelected[i]) {
      isSelected[i] = true;
      result[nth] = ArrangePersonNum[i];
      permu(nth + 1, isSelected, result);
      isSelected[i] = false;
    }
  }
};

const main = () => {
  const input = require('fs')
    .readFileSync('/dev/stdin')
    .toString()
    .trim()
    .split('\n');
  const [n, c] = input[0].split(' ').map((num) => +num);
  N = n;
  C = c;
  input.shift();

  for (let n = 0; n < N; n++) {
    let tempArr = input[0].split(' ');

    // 갖고있는 카드 개수만큼 그 사람 번호를 배열에 넣기
    CntCards.push(+tempArr[0]);
    for (let i = 0; i < CntCards[n]; i++) {
      ArrangePersonNum.push(n + 1);
    }
    tempArr.shift();

    Order.push(tempArr.map((num) => +num));
    input.shift();
  } // 사람마다 갖고있는 카드와 순서 입력
  //   console.log('ArrangePersonNum', ArrangePersonNum);

  for (let c = 0; c < C; c++) {
    Cards.push(input[0].split(',').map((str) => str.split(' ')[1]));
    input.shift();
  } // 각 카드들 입력

  isSelected = new Array(C).fill(false);
  result = new Array(C);
  resultSet = new Set();
  permu(0, isSelected, result);

  let answerStr = '';
  [...answerSet].sort().map((str) => (answerStr += str + '\n'));
  console.log(answerStr);
};

main();