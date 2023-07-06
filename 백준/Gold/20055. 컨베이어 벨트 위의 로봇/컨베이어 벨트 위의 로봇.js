let N;
let K;
let Belt; // 컨베이어 벨트의 내구성 배열
let Robot = []; // 어디에 로봇이 있는지 true/false로
let CntZero = 0; // 내구성이 0인것 개수

const main = () => {
  const input = require('fs')
    .readFileSync('/dev/stdin')
    .toString()
    .trim()
    .split('\n');
  const [n, k] = input[0].split(' ');
  N = n;
  K = k;
  Belt = [...input[1].split(' ').map((num) => +num)];
  Robot = Array.from(Array(N * 2), () => false);

  let Step = 0;
  while (CntZero < K) {
    Step++;

    // 1. 벨트가 로봇과 함께 한 칸 회전
    const beltTmp = Belt.pop();
    Belt.unshift(beltTmp);
    const robotTmp = Robot.pop();
    Robot.unshift(robotTmp);
    // 회전했는데 내릴 로봇이 생겼다?
    if (Robot[N - 1]) {
      Robot[N - 1] = false;
    }

    // 2. 로봇들이 한 칸 이동
    for (let i = N - 2; i >= 0; i--) {
      if (Robot[i]) {
        if (!Robot[i + 1]) {
          // 다음칸에 로봇 없어
          if (Belt[i + 1] > 0) {
            // 다음칸 내구도 0아니야
            // => 이동
            Robot[i] = false;
            Robot[i + 1] = true;
            Belt[i + 1]--;

            // 로봇 내보내는 자리였어
            if (i + 1 === N - 1) {
              Robot[i + 1] = false;
            }
          }
        }
      }
    }

    // 3. 로봇 올림
    if (Belt[0] > 0 && !Robot[0]) {
      Belt[0]--;
      Robot[0] = true;
    }

    // 4. 내구도 0인거 개수세기
    CntZero = Belt.filter((num) => num === 0).length;
  }

  console.log(Step);
};

main();