let N;
let Arr;
let lis = [];

const searchPos = (cur) => {
  let left = 0;
  let right = lis.length - 1;
  let mid;

  while(left <= right) {
    mid = Math.floor((left + right) / 2);

    if(lis[mid] === cur) return mid;
    if(lis[mid] > cur) right = mid - 1;
    else left = mid + 1;
  }

  return left;
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  N = +input.shift();
  Arr = input.shift().split(' ').map(el => +el);

  lis.push(Arr[0]);
  Arr.forEach(cur => {
    if(lis[lis.length - 1] < cur) {
      lis.push(cur);
    }
    else if(lis[lis.length - 1] > cur) {
      const idx = searchPos(cur);
      lis[idx] = cur;
    }
  })

  console.log(lis.length);
}

main();