// 수업 N개
//    각각 s에 시작해서 ~ e에 끝남
// 최소 강의실을 사용해서 모든 수업이 가능하게 하는 강의실 개수 출력
// => 강의가 새로 열렸을 때 +1하고 강의가 끝나면 -1 해서
//    강의가 열린 상태가 최대로 겹치는 개수를 출력
const Lecture = function(time, tag) {
    this.time = time;
    this.tag = tag; // 시작: 1, 끝 : -1
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');
    
    const N = +input.shift();

    let times = [];
    input.forEach(row => {
        const arr = row.split(' ');
        times.push(new Lecture(+arr[0], 1));
        times.push(new Lecture(+arr[1], -1));
    });

    times.sort((a, b) => a.time !== b.time ? a.time - b.time : a.tag - b.tag);
    // console.log(times)

    let cnt = 0;
    let max = 0;
    times.forEach(lec => {
        if(lec.tag === 1) cnt++;
        else cnt--;

        max = Math.max(max, cnt);
    })

    console.log(max);
}

main();