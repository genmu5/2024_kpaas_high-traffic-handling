// import React, { useRef, useEffect } from 'react';
// // import { MapContainer, TileLayer } from 'react-leaflet';
// // import 'leaflet/dist/leaflet.css';
// import { useRecoilValue } from 'recoil';
// import useCurrentLocation from '../../../hooks/useCurrentLocation';
// import { currentLocationAtom } from '../../../state/currentLocationAtom';
//
//
// const icons = [
//     { id: 1, name: '산사태 대피소', icon: '🏔️' },
//     { id: 2, name: '화학사고 대피소', icon: '🧪' },
//     { id: 3, name: '민방위 대피소', icon: '🚨' },
//     { id: 4, name: '이재민 임시 주거 시설', icon: '🏠' },
//     { id: 5, name: '지진시 옥외 대피 장소', icon: '👣' },
//     //{ id: 6, name: '산불', icon: '🔥' },
// ];
//
// const containerStyle = {
//     display: 'flex',
//     flexDirection: 'column',
//     alignItems: 'center',
//     marginTop: '20px', // 컨테이너 상단 여백 추가
// };
//
// const headerStyle = {
//     fontSize: '24px',
//     fontWeight: 'bold',
//     marginBottom: '20px', // 헤더와 아이콘 바 사이 간격 추가
// };
//
// const iconBarStyle = {
//     display: 'flex',
//     justifyContent: 'center',
//     margin: '10px 0',
// };
//
// const iconItemStyle = {
//     margin: '0 10px',
//     textAlign: 'center',
// };
//
// const mapContainerStyle = {
//     flex: 1,
//     width: '650px', // 지도의 너비를 화면의 80%로 조정
//     height: '100%', // 지도의 높이를 500px로 조정
//     // margin: '20px 0', // 지도와 다른 요소 간의 간격 추가
// };
//
// const mapStyle = {
//     width: '100%',
//     height: '100%',
// };
// const ShelterContainer = () => {
//     useCurrentLocation(); // 현재 위치를 가져오는 훅 호출
//     const currentLocation = useRecoilValue(currentLocationAtom);
//     const mapRef = useRef(null);
//
//     useEffect(() => {
//         const naverMapClientId = process.env.REACT_APP_NAVER_MAP_CLIENT_ID;
//
//         const script = document.createElement('script');
//         script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${naverMapClientId}`;
//         script.async = true;
//         document.head.appendChild(script);
//
//         script.onload = () => {
//             if (window.naver) {
//                 const defaultCenter = new window.naver.maps.LatLng(37.554722, 126.970833); // 서울역 좌표
//
//                 // 지도를 초기화할 때, 서울역을 기본 중심으로 설정
//                 mapRef.current = new window.naver.maps.Map('map', {
//                     center: defaultCenter,
//                     zoom: 7,
//                     minZoom: 6,
//                     zoomControl: true,
//                     mapTypeControl: true,
//                     zoomControlOptions: {
//                         position: window.naver.maps.Position.TOP_RIGHT,
//                     },
//                     mapDataControl: false,
//                 });
//
//                 // 여기서 현재 위치로 지도 이동하지 않고, currentLocation 상태 변화를 기다림
//             }
//         };
//
//         return () => {
//             document.head.removeChild(script);
//         };
//     }, []); // 지도 초기화는 한 번만 수행
//
//     // currentLocation 변경 시 지도를 부드럽게 이동
//     useEffect(() => {
//         if (mapRef.current && currentLocation.lat && currentLocation.lng) {
//             const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
//             mapRef.current.panTo(targetLocation, { duration: 1000 }); // 부드러운 이동
//             mapRef.current.setZoom(13, { animate: true, duration: 1000 }); // 애니메이션 줌인 설정
//         }
//     }, [currentLocation]); // currentLocation 변경될 때만 실행
//
//     return (
//         <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '20px' }}>
//             <div style={{ fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>ShelterContainer</div>
//             <div style={{ display: 'flex', justifyContent: 'center', margin: '10px 0' }}>
//                 {icons.map((icon) => (
//                     <div key={icon.id} style={{ margin: '0 10px', textAlign: 'center' }}>
//                         <span>{icon.icon}</span>
//                         <br />
//                         <span>{icon.name}</span>
//                     </div>
//                 ))}
//             </div>
//             <div id="map" style={{ width: '650px', height: '500px' }}></div>
//         </div>
//     );
// };
//
// export default ShelterContainer;


import React, { useRef, useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import useCurrentLocation from '../../../hooks/useCurrentLocation';
import { currentLocationAtom } from '../../../state/currentLocationAtom';

// 지진 대피소 데이터
const earthquakeShelters = [
    {
        "name": "현대빌딩 본관 지하2층",
        "address": "서울특별시 종로구 율곡로 75, 현대빌딩 (계동)",
        "capacity": 6460,
        "lat": 37.57747341738085,
        "lng": 126.98751965704349
    },
    {
        "name": "대동세무고등학교 본관 지하1층",
        "address": "서울특별시 종로구 계동길 84-10, 대동세무고등학교 (계동)",
        "capacity": 222,
        "lat": 37.58105628545779,
        "lng": 126.9874826001711
    },
    {
        "name": "서울농학교 청각언어훈련센터 지하1층",
        "address": "서울특별시 종로구 필운대로 103, 국립서울농학교 청각언어훈련센터동 지하1층 (신교동)",
        "capacity": 240,
        "lat": 37.58413718522873,
        "lng": 126.96884140648686
    },
    {
        "name": "서울맹학교 초등교육관 지하1층",
        "address": "서울특별시 종로구 필운대로 97, 국립서울맹학교 초등교육관동 지하1층 (신교동)",
        "capacity": 378,
        "lat": 37.584199645777986,
        "lng": 126.96822659927734
    },
    {
        "name": "청운효자동주민센터 지하1층",
        "address": "서울특별시 종로구 자하문로 92, 청운효자동주민센터 지하1층 (궁정동)",
        "capacity": 177,
        "lat": 37.584046217936724,
        "lng": 126.97061436144106
    },
    {
        "name": "유림회관 지하1층",
        "address": "서울특별시 종로구 성균관로 31 (명륜3가, 유림회관)",
        "capacity": 3829,
        "lat": 37.58555951495042,
        "lng": 126.99684028752559
    },
    {
        "name": "구기터널",
        "address": "서울특별시 종로구 진흥로 419, 구기터널관리사무소 (구기동)",
        "capacity": 2158,
        "lat": 37.60878995094593,
        "lng": 126.95553788094333
    },
    {
        "name": "서울대학교병원 융합의학기술원(정림빌딩)지하2층 강당",
        "address": "서울특별시 종로구 율곡로 214 (연건동, 정림빌딩)",
        "capacity": 490,
        "lat": 37.575940762389344,
        "lng": 127.00263963295401
    },
    {
        "name": "창신2동주민센터 지하1층",
        "address": "서울특별시 종로구 창신길 62 (창신동, 창신제2동주민센터)",
        "capacity": 309,
        "lat": 37.57440128293274,
        "lng": 127.01078907130506
    },
    {
        "name": "창신2동 동대문맨션 지하1층",
        "address": "서울특별시 종로구 창신길 20 (창신동, 동대문맨션)",
        "capacity": 253,
        "lat": 37.57247163953407,
        "lng": 127.01077666363857
    },
    {
        "name": "우리은행창신동지점 지하1층",
        "address": "서울특별시 종로구 종로 311 (창신동, (주)우리은행창신동지점)",
        "capacity": 160,
        "lat": 37.572209721406836,
        "lng": 127.01227833594984
    },
    {
        "name": "지하철3호선 안국역 지하1~3층 대합실 승강장",
        "address": "서울특별시 종로구 율곡로 지하62, 3호선 안국역 (안국동)",
        "capacity": 12652,
        "lat": 37.57677456605093,
        "lng": 126.98608594405776
    },
    {
        "name": "극동문제연구소 통일관 지하2층 주차장",
        "address": "서울특별시 종로구 북촌로15길 2, 극동문제연구소/통일관 지하2층 (삼청동)",
        "capacity": 910,
        "lat": 37.586652412965506,
        "lng": 126.98353796143849
    }
];

const icons = [
    { id: 1, name: '산사태 대피소', icon: '🏔️' },
    { id: 2, name: '화학사고 대피소', icon: '🧪' },
    { id: 3, name: '민방위 대피소', icon: '🚨' },
    { id: 4, name: '이재민 임시 주거 시설', icon: '🏠' },
    { id: 5, name: '지진시 옥외 대피 장소', icon: '👣' }
];

const ShelterContainer = () => {
    useCurrentLocation();
    const currentLocation = useRecoilValue(currentLocationAtom);
    const mapRef = useRef(null);
    const [shelters, setShelters] = useState([]);
    const [activeIcon, setActiveIcon] = useState(null);
    const markersRef = useRef([]);
    const [activeInfoWindows, setActiveInfoWindows] = useState([]); // 활성화된 정보창 저장용

    useEffect(() => {
        const naverMapClientId = process.env.REACT_APP_NAVER_MAP_CLIENT_ID;

        const script = document.createElement('script');
        script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${naverMapClientId}`;
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
    // currentLocation 변경 시 지도를 부드럽게 이동
    useEffect(() => {
        if (mapRef.current && currentLocation.lat && currentLocation.lng) {
            const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
            mapRef.current.panTo(targetLocation, { duration: 1000 });
            mapRef.current.setZoom(13, { animate: true, duration: 1000 });
        }
    }, [currentLocation]);

    // 마커 생성 및 제거 함수
    const toggleMarkers = (shelterData) => {
        // 기존 마커와 정보창을 모두 제거
        markersRef.current.forEach(marker => marker.setMap(null));
        activeInfoWindows.forEach(infoWindow => infoWindow.close());
        markersRef.current = [];
        setActiveInfoWindows([]);

        if (shelterData.length > 0) {
            shelterData.forEach((shelter) => {
                const marker = new window.naver.maps.Marker({
                    position: new window.naver.maps.LatLng(shelter.lat, shelter.lng),
                    map: mapRef.current,
                    title: shelter.name,
                });

                const infoWindow = new window.naver.maps.InfoWindow({
                    content: `
                        <div>
                            <h4>${shelter.name}</h4>
                            <p>주소: ${shelter.address}</p>
                            <p>수용인원: ${shelter.capacity}명</p>
                        </div>
                    `,
                });

                // 마커 클릭 이벤트 리스너
                window.naver.maps.Event.addListener(marker, 'click', () => {
                    if (infoWindow.getMap()) {
                        infoWindow.close(); // 이미 열려있다면 닫기
                    } else {
                        // 기존에 열려있던 다른 정보창 닫기
                        activeInfoWindows.forEach(activeInfo => activeInfo.close());
                        setActiveInfoWindows([infoWindow]); // 현재 정보창 저장
                        infoWindow.open(mapRef.current, marker); // 정보창 열기
                    }
                });

                markersRef.current.push(marker);
            });
        }
    };

    // 아이콘 클릭 시 대피소 데이터를 설정 및 마커 표시/제거
    const handleIconClick = (iconId) => {
        if (iconId === activeIcon) {
            setActiveIcon(null);
            toggleMarkers([]); // 활성화된 아이콘을 다시 클릭 시 마커 및 정보창 제거
        } else {
            setActiveIcon(iconId);
            if (iconId === 5) {
                setShelters(earthquakeShelters);
                toggleMarkers(earthquakeShelters);
            } else {
                setShelters([]);
                toggleMarkers([]);
            }
        }
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '20px' }}>
            <div style={{ fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>ShelterContainer</div>
            <div style={{ display: 'flex', justifyContent: 'center', margin: '10px 0' }}>
                {icons.map((icon) => (
                    <div
                        key={icon.id}
                        style={{
                            margin: '0 10px',
                            textAlign: 'center',
                            cursor: 'pointer',
                            color: icon.id === activeIcon ? 'blue' : 'black'
                        }}
                        onClick={() => handleIconClick(icon.id)}
                    >
                        <span>{icon.icon}</span>
                        <br />
                        <span>{icon.name}</span>
                    </div>
                ))}
            </div>
            <div id="map" style={{ width: '650px', height: '500px' }}></div>
        </div>
    );
};

export default ShelterContainer;

