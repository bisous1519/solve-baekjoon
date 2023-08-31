let T;
let answer = '';

const palindrome = (str, left, right, cnt) => {
  if (cnt >= 2) return 2;

  while (left < right) {
    // 회문일지도!
    if (str.charAt(left) === str.charAt(right)) {
      left++;
      right--;
      continue;
    }

    // 아님
    const cnt1 = palindrome(str, left + 1, right, cnt + 1);
    const cnt2 = palindrome(str, left, right - 1, cnt + 1);

    return Math.min(cnt1, cnt2);
  }

  return cnt;
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  T = +input.shift();
  for (let t = 1; t <= T; t++) {
    const str = input.shift();

    let left = 0;
    let right = str.length - 1;

    let cnt = palindrome(str, left, right, 0);
    answer += cnt + '\n';
  }

  console.log(answer);
};

main();