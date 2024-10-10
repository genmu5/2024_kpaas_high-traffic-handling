// import React, { useRef, useEffect, useState } from 'react';
// import { useRecoilValue } from 'recoil';
// import useCurrentLocation from '../../../hooks/useCurrentLocation';
// import { currentLocationAtom } from '../../../state/currentLocationAtom';
//
// const icons = [
//     { id: 1, name: '지진', icon: '🌍' },
//     { id: 2, name: '태풍', icon: '🌀' },
//     { id: 3, name: '폭염', icon: '☀️' },
//     { id: 4, name: '한파', icon: '❄️' },
//     { id: 5, name: '호우', icon: '🌧️' },
//     { id: 6, name: '산불', icon: '🔥' },
// ];
//
// const IssueContainer = () => {
//     useCurrentLocation();
//     const currentLocation = useRecoilValue(currentLocationAtom);
//     const mapRef = useRef(null);
//     //const [shelters, setShelters] = useState([]);
//     const [activeIcon, setActiveIcon] = useState(null);
//     //const markersRef = useRef([]);
//     const [activeInfoWindows, setActiveInfoWindows] = useState([]); // 활성화된 정보창 저장용
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
//                 const defaultCenter = new window.naver.maps.LatLng(37.554722, 126.970833);
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
//             }
//         };
//
//         return () => {
//             document.head.removeChild(script);
//         };
//     }, []);
//     // currentLocation 변경 시 지도를 부드럽게 이동
//     useEffect(() => {
//         if (mapRef.current && currentLocation.lat && currentLocation.lng) {
//             const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
//             mapRef.current.panTo(targetLocation, { duration: 1000 });
//             mapRef.current.setZoom(13, { animate: true, duration: 1000 });
//         }
//     }, [currentLocation]);
//
//     return (
//         <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '20px' }}>
//             <div style={{ fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>IssueContainer</div>
//             <div style={{ display: 'flex', justifyContent: 'center', margin: '10px 0' }}>
//                 {icons.map((icon) => (
//                     <div
//                         key={icon.id}
//                         style={{
//                             margin: '0 10px',
//                             textAlign: 'center',
//                             cursor: 'pointer',
//                             color: icon.id === activeIcon ? 'blue' : 'black'
//                         }}
//                         onClick={() => handleIconClick(icon.id)}
//                     >
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

// 수정 전 지도만 띄우는 코드
// export default IssueContainer;

// import React, { useEffect, useRef, useState } from 'react';
// import { useRecoilValue } from 'recoil';
// import useCurrentLocation from '../../../hooks/useCurrentLocation';
// import { currentLocationAtom } from '../../../state/currentLocationAtom';
//
// const IssueContainer = () => {
//     useCurrentLocation();
//     const currentLocation = useRecoilValue(currentLocationAtom);
//     const mapRef = useRef(null);
//     const [disasters, setDisasters] = useState([]); // 재난 데이터를 저장하는 상태
//
//     // Naver 지도 로드 및 초기화
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
//                 const defaultCenter = new window.naver.maps.LatLng(37.554722, 126.970833); // 기본 중심 좌표
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
//                 // 재난 데이터를 API로 가져옴
//                 fetch('/api/disasters/location')
//                     .then((response) => response.json())
//                     .then((data) => {
//                         setDisasters(data); // 재난 데이터 상태에 저장
//
//                         // 지도에 마커 추가
//                         data.forEach((disaster) => {
//                             if (disaster.latitude && disaster.longitude) {
//                                 const marker = new window.naver.maps.Marker({
//                                     position: new window.naver.maps.LatLng(disaster.latitude, disaster.longitude),
//                                     map: mapRef.current,
//                                     title: disaster.regionName,
//                                 });
//
//                                 // 마커 클릭 시 정보창 표시
//                                 const infoWindow = new window.naver.maps.InfoWindow({
//                                     content: `<div style="padding:5px;">${disaster.disasterType} (${disaster.regionName})</div>`,
//                                 });
//
//                                 window.naver.maps.Event.addListener(marker, 'click', () => {
//                                     infoWindow.open(mapRef.current, marker);
//                                 });
//                             }
//                         });
//                     })
//                     .catch((error) => console.error('Error fetching disaster data:', error));
//             }
//         };
//
//         return () => {
//             document.head.removeChild(script);
//         };
//     }, []);
//
//     // 현재 위치가 변경되면 지도를 이동
//     useEffect(() => {
//         if (mapRef.current && currentLocation.lat && currentLocation.lng) {
//             const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
//             mapRef.current.panTo(targetLocation, { duration: 1000 });
//             mapRef.current.setZoom(13, { animate: true, duration: 1000 });
//         }
//     }, [currentLocation]);
//
//     return (
//         <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '20px' }}>
//             <div style={{ fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>IssueContainer</div>
//             <div id="map" style={{ width: '650px', height: '500px' }}></div>
//         </div>
//     );
// };
//
// export default IssueContainer;

// import React, { useEffect, useRef, useState } from 'react';
// import { useRecoilValue } from 'recoil';
// import useCurrentLocation from '../../../hooks/useCurrentLocation';
// import { currentLocationAtom } from '../../../state/currentLocationAtom';
//
// const IssueContainer = () => {
//     useCurrentLocation();
//     const currentLocation = useRecoilValue(currentLocationAtom);
//     const mapRef = useRef(null);
//     const [disasters, setDisasters] = useState([]); // 재난 데이터를 저장하는 상태
//
//     // Naver 지도 로드 및 초기화
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
//                 const defaultCenter = new window.naver.maps.LatLng(37.554722, 126.970833); // 기본 중심 좌표
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
//                 // 재난 데이터를 API로 가져옴
//                 fetch('/api/disasters/location')
//                     .then((response) => response.json())
//                     .then((data) => {
//                         console.log('Fetched disaster data:', data); // 가져온 데이터 출력
//                         setDisasters(data); // 재난 데이터 상태에 저장
//
//                         // 지도에 마커 추가
//                         data.forEach((disaster) => {
//                             if (disaster.latitude && disaster.longitude) {
//                                 console.log(`Adding marker for ${disaster.regionName} at [${disaster.latitude}, ${disaster.longitude}]`);
//
//                                 const marker = new window.naver.maps.Marker({
//                                     position: new window.naver.maps.LatLng(disaster.latitude, disaster.longitude),
//                                     map: mapRef.current,
//                                     title: disaster.regionName,
//                                 });
//
//                                 // 마커 클릭 시 정보창 표시
//                                 const infoWindow = new window.naver.maps.InfoWindow({
//                                     content: `<div style="padding:5px;">${disaster.disasterType} (${disaster.regionName})</div>`,
//                                 });
//
//                                 window.naver.maps.Event.addListener(marker, 'click', () => {
//                                     infoWindow.open(mapRef.current, marker);
//                                 });
//                             } else {
//                                 console.warn(`Missing latitude/longitude for ${disaster.regionName}`);
//                             }
//                         });
//                     })
//                     .catch((error) => console.error('Error fetching disaster data:', error));
//             }
//         };
//
//         return () => {
//             document.head.removeChild(script);
//         };
//     }, []);
//
//     // 현재 위치가 변경되면 지도를 이동
//     useEffect(() => {
//         if (mapRef.current && currentLocation.lat && currentLocation.lng) {
//             const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
//             mapRef.current.panTo(targetLocation, { duration: 1000 });
//             mapRef.current.setZoom(13, { animate: true, duration: 1000 });
//         }
//     }, [currentLocation]);
//
//     return (
//         <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '20px' }}>
//             <div style={{ fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>IssueContainer</div>
//             <div id="map" style={{ width: '650px', height: '500px' }}></div>
//         </div>
//     );
// };
//
// export default IssueContainer;


import React, { useEffect, useRef, useState } from 'react';
import { useRecoilValue } from 'recoil';
import useCurrentLocation from '../../../hooks/useCurrentLocation';
import { currentLocationAtom } from '../../../state/currentLocationAtom';

const IssueContainer = () => {
    useCurrentLocation();
    const currentLocation = useRecoilValue(currentLocationAtom);
    const mapRef = useRef(null);
    const [disasters, setDisasters] = useState([]);

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

                fetch('/api/disasters/location')
                    .then((response) => {
                        if (!response.ok) {
                            throw new Error(`HTTP error! status: ${response.status}`);
                        }
                        return response.json();
                    })
                    .then((data) => {
                        console.log('Fetched disaster data:', data);
                        setDisasters(data);

                        const geocoder = new window.naver.maps.Service();

                        data.forEach((disaster) => {
                            if (disaster.regionName) {
                                geocoder.geocode({ query: disaster.regionName }, (status, response) => {
                                    if (status === window.naver.maps.Service.Status.OK && response.v2.addresses.length > 0) {
                                        const { x, y } = response.v2.addresses[0];

                                        const marker = new window.naver.maps.Marker({
                                            position: new window.naver.maps.LatLng(y, x),
                                            map: mapRef.current,
                                            title: disaster.regionName,
                                        });

                                        const infoWindow = new window.naver.maps.InfoWindow({
                                            content: `<div style="padding:5px;">${disaster.disasterType} (${disaster.regionName})</div>`,
                                        });

                                        window.naver.maps.Event.addListener(marker, 'click', () => {
                                            infoWindow.open(mapRef.current, marker);
                                        });

                                        console.log(`Marker added for ${disaster.regionName} at [${y}, ${x}]`);
                                    } else {
                                        console.warn(`Geocoding failed for ${disaster.regionName}:`, response);
                                    }
                                });
                            }
                        });
                    })
                    .catch((error) => console.error('Error fetching disaster data:', error));
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

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '20px' }}>
            <div style={{ fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>IssueContainer</div>
            <div id="map" style={{ width: '650px', height: '500px' }}></div>
        </div>
    );
};

export default IssueContainer;


