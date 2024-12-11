import './App.css';
import Configuration from "./components/Configuration";
import TicketPoolStatus from "./components/TicketPoolStatus";
import RecentTransactions from "./components/RecentTransactions";
import ControlPanel from "./components/ControlPanel";
import { useState } from "react";

if (typeof global === "undefined") {
  var global = window;
}


function App() {
  const [isPolling, setIsPolling] = useState(false);
  const [totalTickets, setTotalTickets] = useState(0);


const handleStart = () => {
  setIsPolling(true); // Start polling
};

const handleStop = () => {
  setIsPolling(false); // Stop polling
};

  return (
    <div className="min-h-screen bg-blue-900 text-white flex flex-col items-center p-8">
      <h1 className="text-3xl font-bold mb-8">Ticketing System Dashboard</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8 w-full max-w-5xl">
        <Configuration totalTickets={totalTickets} setTotalTickets={setTotalTickets} />
        <TicketPoolStatus totalTickets={totalTickets}/>      
        <RecentTransactions isPolling={isPolling} />
        <ControlPanel onStart={handleStart} onStop={handleStop} />
      </div>
    </div>
  );
};


export default App;