import React, { useRef, useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import useCurrentLocation from '../../../hooks/useCurrentLocation';
import { currentLocationAtom } from '../../../state/currentLocationAtom';

// 재난 유형에 따른 색상 정의
const disasterColors = {
    "지진": 'red',
    "산사태": 'orange',
    "조수": 'blue',
    "폭염": 'yellow',
    "풍수해": 'green',
    "감염병": 'purple',
    "다중밀집건축물붕괴대형사고": 'black',
    "산불": 'brown',
    "초미세먼지": 'gray',
    "해양선박사고": 'darkblue',
    "한파": 'lightblue',
    "화재": 'darkred',
    "호우": 'lightgreen'
};

const IssueContainer = () => {
    useCurrentLocation();
    const currentLocation = useRecoilValue(currentLocationAtom);
    const mapRef = useRef(null);
    const [disasterData, setDisasterData] = useState([]);
    const markersRef = useRef([]);
    const infoWindowsRef = useRef([]);

    useEffect(() => {
        // API에서 재난 데이터 가져오기
        const fetchDisasterData = async () => {
            try {
                const response = await fetch(`http://default-backend-service-09278-100059673-08700d08cf31.kr.lb.naverncp.com:8080/api/disasters/location`);
                const data = await response.json();
                console.log("Fetched disaster data:", data); // API 응답 로그 확인
                setDisasterData(data);
            } catch (error) {
                console.error("Error fetching disaster data:", error);
            }
        };

        fetchDisasterData();
    }, []);

    useEffect(() => {
        const naverMapClientId = process.env.REACT_APP_NAVER_MAP_CLIENT_ID;

        // 지도 스크립트 로드
        const script = document.createElement('script');
        script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${naverMapClientId}&submodules=geocoder`;
        script.async = true;
        document.head.appendChild(script);

        script.onload = () => {
            if (window.naver) {
                const defaultCenter = new window.naver.maps.LatLng(37.554722, 126.970833);
                mapRef.current = new window.naver.maps.Map('map', {
                    center: defaultCenter,
                    zoom: 7,
                    minZoom: 6,
                    zoomControl: true,
                    mapTypeControl: true,
                    zoomControlOptions: {
                        position: window.naver.maps.Position.TOP_RIGHT,
                    },
                    mapDataControl: false,
                });
            }
        };

        return () => {
            document.head.removeChild(script);
        };
    }, []);

    useEffect(() => {
        if (mapRef.current && disasterData.length > 0) {
            // 기존 마커와 정보창 모두 제거
            markersRef.current.forEach(marker => marker.setMap(null));
            infoWindowsRef.current.forEach(infoWindow => infoWindow.close());
            markersRef.current = [];
            infoWindowsRef.current = [];

            disasterData.forEach((disaster) => {
                const { latitude, longitude, disasterType, regionName } = disaster;
                const color = disasterColors[disasterType] || 'black'; // 재난 유형에 맞는 색상 선택

                if (latitude && longitude) {
                    const position = new window.naver.maps.LatLng(latitude, longitude);

                    // 마커에 색상만 적용
                    const marker = new window.naver.maps.Marker({
                        position,
                        map: mapRef.current,
                        title: `${disasterType} - ${regionName}`,
                        icon: {
                            content: `<div style="width: 16px; height: 16px; background-color: ${color}; border-radius: 50%;"></div>`,
                            anchor: new window.naver.maps.Point(8, 8), // 중앙에 위치시키기 위해 anchor 조정
                        },
                    });

                    // 색상 적용된 정보창 생성
                    const infoWindow = new window.naver.maps.InfoWindow({
                        content: `<div style="padding:10px; background-color: ${color}; color: #ffffff; border-radius: 5px;">${disasterType} - ${regionName}</div>`,
                        borderWidth: 0,  // 말풍선 테두리 제거
                        backgroundColor: "transparent",  // 말풍선 외부 투명하게 설정
                    });

                    infoWindowsRef.current.push(infoWindow);

                    // 마커 클릭 시 정보창 열고 닫기
                    let infoWindowOpen = false;
                    window.naver.maps.Event.addListener(marker, "click", () => {
                        if (infoWindowOpen) {
                            infoWindow.close();
                        } else {
                            infoWindow.open(mapRef.current, marker);
                        }
                        infoWindowOpen = !infoWindowOpen;
                    });

                    // 마커 참조 저장
                    markersRef.current.push(marker);
                } else {
                    console.warn(`Missing latitude or longitude for disaster: ${disasterType} at ${regionName}`);
                }
            });

            console.log("Markers created:", markersRef.current); // 마커 생성 여부 확인 로그
        }
    }, [disasterData]);

    useEffect(() => {
        if (mapRef.current && currentLocation.lat && currentLocation.lng) {
            const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
            mapRef.current.panTo(targetLocation, { duration: 1000 });
            mapRef.current.setZoom(10, { animate: true, duration: 1000 });
        }
    }, [currentLocation]);

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '20px' }}>
            <div style={{ fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>IssueContainer</div>
            <div id="map" style={{ width: '650px', height: '500px' }}></div>
        </div>
    );
};

export default IssueContainer;

