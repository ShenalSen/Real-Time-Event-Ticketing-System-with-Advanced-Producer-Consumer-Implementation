import { useState } from "react";
import ControlPanel from "./ControlPanel";
import RecentTransactions from "./RecentTransactions";

function App() {
  const [isPolling, setIsPolling] = useState(false);

  const handleStart = () => {
    setIsPolling(true); 
  };

  const handleStop = () => {
    setIsPolling(false); 
  };

  return (
    <div className="p-10">
      <ControlPanel onStart={handleStart} onStop={handleStop} />
      <RecentTransactions isPolling={isPolling} />
    </div>
  );
}

export default App;
