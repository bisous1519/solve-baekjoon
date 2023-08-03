// 참고 코드
const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  const s = input.shift().trim(); // 문자열 s
  const t = input.shift().trim(); // 문자열 t
  let answer = 0;

  // 최장 부분 문자열을 찾아 길이를 저장할 이차원 배열
  // 초기화
  let LCS = new Array(s.length + 1);
  for (let i = 0; i < LCS.length; i++) {
    LCS[i] = new Array(t.length + 1).fill(0);
  }

  for (let i = 1; i <= s.length; i++) {
    for (let j = 1; j <= t.length; j++) {
      if (s[i - 1] === t[j - 1]) {
        // 동일한 문자라면 각 문자열의 직전 문자들의 최장부분문자열찾은 값에 +1 해서 저장
        LCS[i][j] = LCS[i - 1][j - 1] + 1;
      } else LCS[i][j] = 0;

      // 지금까지 중 최장 문자열 길이면 갱신
      if (LCS[i][j] > answer) answer = LCS[i][j];
    }
  }

  console.log(answer);
};

main();