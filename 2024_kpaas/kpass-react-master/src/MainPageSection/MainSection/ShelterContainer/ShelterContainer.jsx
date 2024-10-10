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
    const [activeInfoWindows, setActiveInfoWindows] = useState([]);

    useEffect(() => {
        const naverMapClientId = process.env.REACT_APP_NAVER_MAP_CLIENT_ID;

        const script = document.createElement('script');
        script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${naverMapClientId}`;
        script.async = true;
        document.head.appendChild(script);

        script.onload = () => {
            if (window.naver) {
                const defaultCenter = new window.naver.maps.LatLng(37.554722, 126.970833); // 서울역 좌표
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
        if (mapRef.current && currentLocation.lat && currentLocation.lng) {
            const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
            mapRef.current.panTo(targetLocation, { duration: 1000 });
            mapRef.current.setZoom(13, { animate: true, duration: 1000 });
        }
    }, [currentLocation]);

    const fetchShelters = async (url) => {
        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error('Failed to fetch data');
            const data = await response.json();
            setShelters(data);
        } catch (error) {
            console.error('Error fetching shelter data:', error);
        }
    };

    const handleIconClick = (iconId) => {
        if (iconId === activeIcon) {
            setActiveIcon(null);
            toggleMarkers([]); // 활성화된 아이콘을 다시 클릭 시 마커 및 정보창 제거
        } else {
            setActiveIcon(iconId);
            let apiUrl = '';

            // 아이콘 ID에 따라 API URL 설정
            switch (iconId) {
                case 1: apiUrl = '/api/shelters/landslide'; break;
                case 2: apiUrl = '/api/shelters/chemical'; break;
                case 3: apiUrl = '/api/shelters/civil-defense'; break;
                case 4: apiUrl = '/api/shelters/disaster-victims'; break;
                case 5: apiUrl = '/api/shelters/earthquake'; break;
                default: break;
            }

            if (apiUrl) {
                fetchShelters(apiUrl);
            }
        }
    };

    const toggleMarkers = (shelterData) => {
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

                window.naver.maps.Event.addListener(marker, 'click', () => {
                    if (infoWindow.getMap()) {
                        infoWindow.close();
                    } else {
                        activeInfoWindows.forEach(activeInfo => activeInfo.close());
                        setActiveInfoWindows([infoWindow]);
                        infoWindow.open(mapRef.current, marker);
                    }
                });

                markersRef.current.push(marker);
            });
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
