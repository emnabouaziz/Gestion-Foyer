import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    stages: [
        { duration: '30s', target: 20 },
        { duration: '1m', target: 20 },
        { duration: '30s', target: 0 },
    ],
};

export default function () {
    let res = http.get('http://192.168.50.4:8089/tpfoyer/universite/retrieve-all-universites');

    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1); 
}