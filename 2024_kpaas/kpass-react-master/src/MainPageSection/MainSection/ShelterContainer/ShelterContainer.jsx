import React, { useRef, useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import useCurrentLocation from '../../../hooks/useCurrentLocation';
import { currentLocationAtom } from '../../../state/currentLocationAtom';
import styled from "styled-components";

const icons = [
    { id: 1, name: '산사태 대피소', icon: '🏔️' },
    { id: 2, name: '화학사고 대피소', icon: '🧪' },
    { id: 3, name: '민방위 대피소', icon: '🚨' },
    { id: 4, name: '이재민 임시 주거 시설', icon: '🏠' },
    { id: 5, name: '지진시 옥외 대피 장소', icon: '👣' }
];

const Container = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
`;

const Title = styled.div`
    font-size: 28px;
    font-weight: 600;
    color: #333;
    margin-bottom: 20px;
    text-align: left;
    width: 100%;
    border-bottom: 2px solid #ddd;
    padding-bottom: 8px;
`;

const IconContainer = styled.div`
    display: flex;
    justify-content: center;
    margin: 10px 0;
`;

const MapContainer = styled.div`
    width: 650px;
    height: 500px;
`;

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

                window.naver.maps.Event.addListener(mapRef.current, 'bounds_changed', () => {
                    const bounds = mapRef.current.getBounds();
                    const southWest = bounds.getSW();
                    const northEast = bounds.getNE();
                    const zoomLevel = mapRef.current.getZoom();

                    if (activeIcon) {
                        fetchSheltersInBounds(southWest, northEast, zoomLevel);
                    }
                });
            }
        };

        return () => {
            document.head.removeChild(script);
        };
    }, []);

    const fetchSheltersInBounds = async (southWest, northEast, zoomLevel) => {
        const url = `http://211.188.55.193:8080/api/shelters?swLat=${southWest.lat()}&swLng=${southWest.lng()}&neLat=${northEast.lat()}&neLng=${northEast.lng()}&zoom=${zoomLevel}`;
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
            toggleMarkers(shelters);
        }
    }, [shelters]);

    const fetchShelters = async (url) => {
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
            setShelters(data);
        } catch (error) {
            console.error('Error fetching shelter data:', error);
        }
    };

    const handleIconClick = (iconId) => {
        if (iconId === activeIcon) {
            setActiveIcon(null);
            toggleMarkers([]);
        } else {
            setActiveIcon(iconId);
            let apiUrl = '';

            switch (iconId) {
                case 1:
                    apiUrl = `http://211.188.55.193:8080/api/shelters/landslide`;
                    break;
                case 2:
                    apiUrl = `http://211.188.55.193:8080/api/shelters/chemical`;
                    break;
                case 3: {
                    const bounds = mapRef.current.getBounds();
                    const southWest = bounds.getSW();
                    const northEast = bounds.getNE();
                    const zoomLevel = mapRef.current.getZoom();
                    apiUrl = `http://211.188.55.193:8080/api/shelters/civil-defense?swLat=${southWest.lat()}&swLng=${southWest.lng()}&neLat=${northEast.lat()}&neLng=${northEast.lng()}&zoom=${zoomLevel}`;
                    break;
                }
                case 4: {
                    const bounds = mapRef.current.getBounds();
                    const southWest = bounds.getSW();
                    const northEast = bounds.getNE();
                    const zoomLevel = mapRef.current.getZoom();
                    apiUrl = `http://211.188.55.193:8080/api/shelters/disaster-victims?swLat=${southWest.lat()}&swLng=${southWest.lng()}&neLat=${northEast.lat()}&neLng=${northEast.lng()}&zoom=${zoomLevel}`;
                    break;
                }

                case 5:{
                    const bounds = mapRef.current.getBounds();
                    const southWest = bounds.getSW();
                    const northEast = bounds.getNE();
                    const zoomLevel = mapRef.current.getZoom();
                    apiUrl = `http://211.188.55.193:8080/api/shelters/earthquake?swLat=${southWest.lat()}&swLng=${southWest.lng()}&neLat=${northEast.lat()}&neLng=${northEast.lng()}&zoom=${zoomLevel}`;
                    break;
                }
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
        <Container>
            <Title>주변 대피소</Title>
            <IconContainer>
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
            </IconContainer>
            <MapContainer id="map"></MapContainer>
        </Container>
    );
};

export default ShelterContainer;
