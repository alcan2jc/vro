import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar } from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Tab1.css';

import React from 'react';
import useTeller from '../services/tellerHook';

const Tab1: React.FC = () => {
    const { open, ready } = useTeller();

    return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Tab 1</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonHeader collapse="condense">
          <IonToolbar>
            <IonTitle size="large">Tab 1</IonTitle>
          </IonToolbar>
        </IonHeader>
        <ExploreContainer name="Tab 1 page" />

        <div>
            <button onClick={open} disabled={!ready}>
                Connect Your Bank
            </button>
        </div>
        
      </IonContent>
    </IonPage>
  );
};

export default Tab1;
