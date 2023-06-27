let left = 0;
let right = 0;
let arr = [];
let sum;

function moveRight() {
    right++;
    if(right === arr.length) {
        return false; // 배열 길이 밖
    }
    sum += arr[right];
    return true;
}

function moveLeft() {
    sum -= arr[left];
    left++;
    if(left <= right) {
        return true;
    }else return false;
}

function solution(sequence, k) {
    arr = [...sequence];
    sum = sequence[0];
    let answer = [];
    
    while(true) {
        if(sum === k) {
            if(answer.length === 0) {
                answer.push(left);
                answer.push(right);
            }else if(answer[1] - answer[0] + 1 > right - left + 1) {
                answer = [left, right];
            }
            if(!!!moveRight()) break;
            
        }
        
        else if(sum < k) {
            if(!!!moveRight()) break;
        }else if(sum > k) {
            if(!!!moveLeft()) {
                if(!!!moveRight()) break;
            }
        }
    }
    
    return answer;
}