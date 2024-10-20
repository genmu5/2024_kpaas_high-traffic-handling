// import React, { useRef, useEffect, useState } from 'react';
// import { useRecoilValue } from 'recoil';
// import useCurrentLocation from '../../../hooks/useCurrentLocation';
// import { currentLocationAtom } from '../../../state/currentLocationAtom';
//
// const icons = [
//     { id: 1, name: '산사태 대피소', icon: '🏔️' },
//     { id: 2, name: '화학사고 대피소', icon: '🧪' },
//     { id: 3, name: '민방위 대피소', icon: '🚨' },
//     { id: 4, name: '이재민 임시 주거 시설', icon: '🏠' },
//     { id: 5, name: '지진시 옥외 대피 장소', icon: '👣' }
// ];
//
// const ShelterContainer = () => {
//     useCurrentLocation();
//     const currentLocation = useRecoilValue(currentLocationAtom);
//     const mapRef = useRef(null);
//     const [shelters, setShelters] = useState([]);
//     const [activeIcon, setActiveIcon] = useState(null);
//     const markersRef = useRef([]);
//     const [activeInfoWindows, setActiveInfoWindows] = useState([]);
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
//                 // 지도 경계가 변경될 때마다 데이터를 가져오는 로직 추가
//                 window.naver.maps.Event.addListener(mapRef.current, 'bounds_changed', () => {
//                     const bounds = mapRef.current.getBounds();
//                     const southWest = bounds.getSW();
//                     const northEast = bounds.getNE();
//                     const zoomLevel = mapRef.current.getZoom();
//
//                     // 지진 대피소가 선택된 경우에만 경계 내 데이터를 다시 요청
//                     if (activeIcon === 5) {
//                         fetchSheltersInBounds(southWest, northEast, zoomLevel);  // 이 함수 호출로 변경
//                     }
//                 });
//             }
//         };
//
//         return () => {
//             document.head.removeChild(script);
//         };
//     }, [activeIcon]);  // activeIcon이 변경될 때마다 해당 useEffect 실행
//
//
//     const fetchSheltersInBounds = async (southWest, northEast, zoomLevel) => {
//         console.log('Fetching shelters within bounds:', southWest, northEast, zoomLevel);
//         const url = `/api/shelters?swLat=${southWest.lat()}&swLng=${southWest.lng()}&neLat=${northEast.lat()}&neLng=${northEast.lng()}&zoom=${zoomLevel}`;
//         fetchShelters(url);
//     };
//
//     useEffect(() => {
//         if (mapRef.current && currentLocation.lat && currentLocation.lng) {
//             const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
//             mapRef.current.panTo(targetLocation, { duration: 1000 });
//             mapRef.current.setZoom(13, { animate: true, duration: 1000 });
//         }
//     }, [currentLocation]);
//
//     useEffect(() => {
//         if (shelters.length > 0) {
//             console.log('Updating markers with shelter data:', shelters);
//             toggleMarkers(shelters);
//         }
//     }, [shelters]);
//
//     const fetchShelters = async (url) => {
//         console.log('Fetching from URL:', url);
//         try {
//             const response = await fetch(url, {
//                 credentials: 'include',
//                 headers: {
//                     'Content-Type': 'application/json',
//                     'Accept': 'application/json',
//                 }
//             });
//             if (!response.ok) throw new Error('Failed to fetch data');
//             const data = await response.json();
//             console.log('Fetched shelters data:', data);
//             setShelters(data);
//         } catch (error) {
//             console.error('Error fetching shelter data:', error);
//         }
//     };
//
//     const handleIconClick = (iconId) => {
//         if (iconId === activeIcon) {
//             setActiveIcon(null);
//             toggleMarkers([]);  // 활성화된 아이콘을 다시 클릭 시 마커 및 정보창 제거
//         } else {
//             setActiveIcon(iconId);  // 아이콘 활성화 상태 변경
//             let apiUrl = '';
//
//             // 아이콘에 따라 API URL을 결정
//             switch (iconId) {
//                 case 1: apiUrl = '/api/shelters/landslide'; break;
//                 case 2: apiUrl = '/api/shelters/chemical'; break;
//                 case 3: apiUrl = '/api/shelters/civil-defense'; break;
//                 case 4: apiUrl = '/api/shelters/disaster-victims'; break;
//                 case 5:
//                     // 지진 대피소의 경우, 현재 지도의 경계 및 줌 정보를 사용
//                     const bounds = mapRef.current.getBounds();
//                     const southWest = bounds.getSW();
//                     const northEast = bounds.getNE();
//                     const zoomLevel = mapRef.current.getZoom();
//
//                     // 지도 중심을 변경하지 않고 데이터만 요청
//                     apiUrl = `/api/shelters/earthquake?swLat=${southWest.lat()}&swLng=${southWest.lng()}&neLat=${northEast.lat()}&neLng=${northEast.lng()}&zoom=${zoomLevel}`;
//                     break;
//                 default: break;
//             }
//
//             // URL이 정의되면 해당 URL로 데이터를 요청
//             if (apiUrl) {
//                 fetchShelters(apiUrl);  // 마커 데이터 요청
//             }
//         }
//     };
//
//
//     const toggleMarkers = (shelterData) => {
//         console.log('Shelter data for markers:', shelterData);
//         markersRef.current.forEach(marker => marker.setMap(null));
//         activeInfoWindows.forEach(infoWindow => infoWindow.close());
//         markersRef.current = [];
//         setActiveInfoWindows([]);
//
//         if (shelterData.length > 0) {
//             shelterData.forEach((shelter) => {
//                 console.log('Creating marker at:', shelter.lat, shelter.lng);
//                 const marker = new window.naver.maps.Marker({
//                     position: new window.naver.maps.LatLng(shelter.lat, shelter.lng),
//                     map: mapRef.current,
//                     title: shelter.name,
//                 });
//
//                 const infoWindow = new window.naver.maps.InfoWindow({
//                     content: `
//                         <div>
//                             <h4>${shelter.name}</h4>
//                             <p>주소: ${shelter.address}</p>
//                             <p>수용인원: ${shelter.capacity}명</p>
//                         </div>
//                     `,
//                 });
//
//                 window.naver.maps.Event.addListener(marker, 'click', () => {
//                     if (infoWindow.getMap()) {
//                         infoWindow.close();
//                     } else {
//                         activeInfoWindows.forEach(activeInfo => activeInfo.close());
//                         setActiveInfoWindows([infoWindow]);
//                         infoWindow.open(mapRef.current, marker);
//                     }
//                 });
//
//                 markersRef.current.push(marker);
//             });
//         }
//     };
//
//     return (
//         <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginTop: '20px' }}>
//             <div style={{ fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>ShelterContainer</div>
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

                // 지도 경계가 변경될 때마다 데이터를 가져오는 로직 추가
                window.naver.maps.Event.addListener(mapRef.current, 'bounds_changed', () => {
                    const bounds = mapRef.current.getBounds();
                    const southWest = bounds.getSW();
                    const northEast = bounds.getNE();
                    const zoomLevel = mapRef.current.getZoom();

                    if (activeIcon === 5) {
                        fetchSheltersInBounds(southWest, northEast, zoomLevel);
                    }
                });
            }
        };

        return () => {
            document.head.removeChild(script);
        };
    }, []); // activeIcon 제거, 초기 로딩 시 한 번만 실행되도록

    const fetchSheltersInBounds = async (southWest, northEast, zoomLevel) => {
        console.log('Fetching shelters within bounds:', southWest, northEast, zoomLevel);
        const url = `/api/shelters?swLat=${southWest.lat()}&swLng=${southWest.lng()}&neLat=${northEast.lat()}&neLng=${northEast.lng()}&zoom=${zoomLevel}`;
        fetchShelters(url);
    };

    useEffect(() => {
        if (mapRef.current && currentLocation.lat && currentLocation.lng) {
            const targetLocation = new window.naver.maps.LatLng(currentLocation.lat, currentLocation.lng);
            mapRef.current.panTo(targetLocation, { duration: 1000 });
            mapRef.current.setZoom(13, { animate: true, duration: 1000 });
        }
    }, [currentLocation]);

    useEffect(() => {
        if (shelters.length > 0) {
            console.log('Updating markers with shelter data:', shelters);
            toggleMarkers(shelters);
        }
    }, [shelters]);

    const fetchShelters = async (url) => {
        console.log('Fetching from URL:', url);
        try {
            const response = await fetch(url, {
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                }
            });
            if (!response.ok) throw new Error('Failed to fetch data');
            const data = await response.json();
            console.log('Fetched shelters data:', data);
            setShelters(data);
        } catch (error) {
            console.error('Error fetching shelter data:', error);
        }
    };

    const handleIconClick = (iconId) => {
        if (iconId === activeIcon) {
            setActiveIcon(null);
            toggleMarkers([]);  // 활성화된 아이콘을 다시 클릭 시 마커 및 정보창 제거
        } else {
            setActiveIcon(iconId);
            let apiUrl = '';

            // 아이콘에 따라 API URL 결정
            switch (iconId) {
                case 1: apiUrl = '/api/shelters/landslide'; break;
                case 2: apiUrl = '/api/shelters/chemical'; break;
                case 3: apiUrl = '/api/shelters/civil-defense'; break;
                case 4: apiUrl = '/api/shelters/disaster-victims'; break;
                case 5:
                    const bounds = mapRef.current.getBounds();
                    const southWest = bounds.getSW();
                    const northEast = bounds.getNE();
                    const zoomLevel = mapRef.current.getZoom();
                    apiUrl = `/api/shelters/earthquake?swLat=${southWest.lat()}&swLng=${southWest.lng()}&neLat=${northEast.lat()}&neLng=${northEast.lng()}&zoom=${zoomLevel}`;
                    break;
                default: break;
            }

            if (apiUrl) {
                fetchShelters(apiUrl);
            }
        }
    };

    const toggleMarkers = (shelterData) => {
        console.log('Shelter data for markers:', shelterData);
        markersRef.current.forEach(marker => marker.setMap(null));
        activeInfoWindows.forEach(infoWindow => infoWindow.close());
        markersRef.current = [];
        setActiveInfoWindows([]);

        if (shelterData.length > 0) {
            shelterData.forEach((shelter) => {
                console.log('Creating marker at:', shelter.lat, shelter.lng);
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



