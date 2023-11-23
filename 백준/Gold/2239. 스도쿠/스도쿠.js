// 81자리, 9개 숫자 => 9^81..?
// 백트래킹을 잘하면 되는건가 빡구현인데

let Map;
let row;
let col;
let box;
let blank;
let blankLen;
let fin = false;

const printMap = () => {
  console.log(Map.map(row => row.join('')).join('\n'));
}

const sudoku = (nth) => {
  if(fin) return;
  
  if(nth === blankLen) {
    printMap();
    fin = true;
    return;
  }

  const r = blank[nth][0];
  const c = blank[nth][1];

  for(let num = 1; num <= 9; num ++) {
    const a = Math.floor(r/3) * 3 + Math.floor(c/3);
    if(!row[r][num] && !col[num][c] && !box[a][num]) {
      row[r][num] = true;
      col[num][c] = true;
      box[a][num] = true;
      Map[r][c] = num;

      sudoku(nth + 1);
      if(fin) return;

      row[r][num] = false;
      col[num][c] = false;
      box[a][num] = false;
      Map[r][c] = 0;
    }
  }
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  blank = [];
  Map = input.map((row, r) => row.split('').map((el, c) => {
    if(+el === 0) blank.push([r, c]);
    return +el
  }));
  blankLen = blank.length;

  row = new Array(9).fill(null).map(() => new Array(10).fill(false));
  col = new Array(10).fill(null).map(() => new Array(9).fill(false));
  box = new Array(9).fill(null).map(() => new Array(10).fill(false));

  // 사용한 숫자 체크
  //   -> row
  for(let r=0; r<9; r++) {
    for(let c=0; c<9; c++) {
      row[r][Map[r][c]] = true;
    }
  }
  //   -> col
  for(let r=0; r<9; r++) {
    for(let c=0; c<9; c++) {  
      col[Map[r][c]][c] = true;
    }
  }
  //   -> box
  for(let a=0; a<9; a++) {
    const _r = Math.floor(a/3) * 3;
    const _c = a%3 * 3;
    for(let r=_r; r<_r+3; r++) {
      for(let c=_c; c<_c+3; c++) {
        box[a][Map[r][c]] = true; 
      }
    }
  }

  sudoku(0);
}

main();