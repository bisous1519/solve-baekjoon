// 벌집 M*M
//   각 칸에 애벌레 한마리
//   (0, 0) ~ (M-1, M-1)
// 애벌레
//   첫 날은 모두 크기가 1
//   정오에 한번 자람
//      +0, +1, +2 중에
//   N일 동안 반복

let M, N;
let Map;
const dr = [0, -1, -1]; // L, D, U
const dc = [-1, -1, 0];

const Larva = function(size, grow) {
    this.size = size;
    this.grow = grow;
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    [M, N] = input.shift().split(' ').map(el => +el);

    Map = new Array(M).fill(null).map(() => new Array(M).fill(new Larva(1, 0)));

    input.forEach(row => {
        let grow = row.split(' ').map(el => +el);

        // 입력받은대로 첫열, 첫행 자라게 하고
        let g = 0;
        for(let i=M-1; i>=0; i--) {
            if(grow[g] === 0) {
                g++;
                i++;
                continue;
            }

            Map[i][0] = new Larva(Map[i][0].size + g, g);
            grow[g]--;
        }
        for(let i=1; i<M; i++) {
            if(grow[g] === 0) {
                g++;
                i--;
                continue;
            }

            Map[0][i] = new Larva(Map[0][i].size + g, g);
            grow[g]--;
        }

        // 첫열, 첫행 자란거대로 나머지 배열 채우기
        for(let r=1; r<M; r++) {
            for(let c=1; c<M; c++) {
                let maxGrow = 0;
                for(let d=0; d<3; d++) {
                    const goR = r + dr[d];
                    const goC = c + dc[d];
                    maxGrow = Math.max(maxGrow, Map[goR][goC].grow);
                }

                Map[r][c] = new Larva(Map[r][c].size + maxGrow, maxGrow);
            }
        }

        // console.log(Map.map(row => row.map(el => el.size).join(' ')).join('\n'));
        // console.log('--------')
    })

    console.log(Map.map(row => row.map(el => el.size).join(' ')).join('\n'));
}

main();