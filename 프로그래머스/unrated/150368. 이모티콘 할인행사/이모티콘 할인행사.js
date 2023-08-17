let N;
let M;
let maxSubmit = -1; // 임티플 최대 가입자 수
let maxIncome = -1; // 임티 최대 판매액
let off; // 각 임티 할인율

function solution(users, emoticons) {
  N = users.length;
  M = emoticons.length;

  off = new Array(M).fill(10);
  for (let i = 0; i < Math.pow(4, M); i++) {
    // off 배열 모양으로 임티 할인할 때 각 이모티콘 가격
    let emo = [];
    for (let m = 0; m < M; m++) {
      emo.push((emoticons[m] * (100 - off[m])) / 100);
    }

    // 현 할인율에서 유저들이 이모티콘 사고 임티플 가입
    let submit = 0;
    let income = 0;
    nextUser: for (let n = 0; n < N; n++) {
      // n번 유저
      let cost = 0;
      for (let m = 0; m < M; m++) {
        // m번 임티
        // 이 사용자가 원하는 할인율보다 높으면 삼
        if (off[m] >= users[n][0]) {
          cost += emo[m];
        }

        // 이 사용자의 마지노선 가격보다 넘어가면 임티플함
        if (cost >= users[n][1]) {
          submit++;
          continue nextUser; // 다음유저~
        }
      }

      // 이 사용자는 마지노선 가격보다 임티 적게 삼
      income += cost;
    }

    // 이번 경우에서 임티플 가입자 수랑 판매액 비교해서 갱신
    if (maxSubmit < submit) {
      maxSubmit = submit;
      maxIncome = income;
    } else if (maxSubmit === submit && maxIncome < income) {
      maxIncome = income;
    }

    // 할인율 바꾸기
    for (let m = 0; m < M; m++) {
      off[m] += 10;
      if (off[m] < 50) break;
      off[m] = 10;
    }
  }

  let answer = [maxSubmit, maxIncome];
  return answer;
}
