<?php


/**
 * Description of GCM
 *
 * @author Ravi Tamada
 */
class APNS {

    //put your code here
    // constructor
    

    /**
     * Sending Push Notification
     */
    public function send_notification($registatoin_ids, $message) {
        // include config
       // Put your device token here (without spaces):
		$deviceToken = $registatoin_ids;

	// Put your private key's passphrase here:
	$passphrase = '123456';

	
////////////////////////////////////////////////////////////////////////////////

$ctx = stream_context_create();
stream_context_set_option($ctx, 'ssl', 'local_cert', './controller/dck.pem');
stream_context_set_option($ctx, 'ssl', 'passphrase', $passphrase);

// Open a connection to the APNS server
$fp = stream_socket_client(
	'ssl://gateway.sandbox.push.apple.com:2195', $err,
	$errstr, 60, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT, $ctx);

if (!$fp)
	exit("Failed to connect: $err $errstr" . PHP_EOL);

echo 'Connected to APNS' . PHP_EOL;

// Create the payload body
 $body['aps'] = array(
	'content-available' => 1,
	'alert' => $message,
	'sound' => 'default',
	'badge' => 1
	);

// Encode the payload as JSON
$payload = json_encode($body);
/*
$body['aps'] = array(
	'alert' => $message,
	'sound' => 'default'
	);

// Encode the payload as JSON
$payload = json_encode($body);
$payload = '"{\"aps\":{\"alert\":\"'.$message.'\",\"sound\":\"default\",\"badge\":1}}"';
*/
// Build the binary notification
$msg = chr(0) . pack('n', 32) . pack('H*', $deviceToken) . pack('n', strlen($payload)) . $payload;

// Send it to the server
$result = fwrite($fp, $msg, strlen($msg));

if (!$result)
	echo 'Message not delivered' . PHP_EOL;
else
	echo 'Message successfully delivered' . PHP_EOL;

// Close the connection to the server
fclose($fp);
    }

}

?>
