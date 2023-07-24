const main = () => {
  let input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input2.txt`
    )
    .toString()
    .trim()
    .split('');
  const len = input.length;
  const cntA = input.filter((el) => el === 'a').length;
  const window = input.slice(0, cntA); // 슬라이딩윈도우 배열 초기값 : 맨앞부터 cntA개 원소

  let cntB = window.filter((el) => el === 'b').length;
  let Min = cntB;
  let nextIdx = cntA;
  // 문자열 총 길이만큼 하나씩 옮겨가며 슬라이딩윈도우로 확인
  for (let i = 0; i < len; i++) {
    const shiftEl = window.shift();
    if (shiftEl === 'b') cntB--;
    input.push(shiftEl);

    const pushEl = input[nextIdx++];
    if (pushEl === 'b') cntB++;
    window.push(pushEl);

    Min = Math.min(Min, cntB);
  }

  console.log(Min);
};

main();