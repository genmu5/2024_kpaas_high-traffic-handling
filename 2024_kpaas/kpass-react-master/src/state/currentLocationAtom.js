import { atom } from 'recoil';

export const currentLocationAtom = atom({
    key: 'currentLocation',
    default: { lat: 37.5666103, lng: 126.9783882 }, // 서울시청 좌표 기본값
});