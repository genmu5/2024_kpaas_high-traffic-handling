import { useEffect } from 'react';
import { useSetRecoilState } from 'recoil';
import { currentLocationAtom } from '../state/currentLocationAtom';

const useCurrentLocation = () => {
    const setCurrentLocation = useSetRecoilState(currentLocationAtom);

    // 내 현재 위치 값 번환 성공 시 실행 함수 -> 내 현재 위치 값을 currentMyLocationAtom에 저장
    useEffect(() => {
        const success = (location) => {
            setCurrentLocation({
                lat: location.coords.latitude,
                lng: location.coords.longitude,
            });
        };
        // 내 현재 위치 값 반환 실패 시 실행 함수 -> 지도 중심을 서울시청 위치로 설정
        const error = () => {
            setCurrentLocation({ lat: 37.5666103, lng: 126.9783882 }); // 오류 시 서울시청 좌표 설정
        };

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(success, error);
        }
    }, [setCurrentLocation]);
};

export default useCurrentLocation;
