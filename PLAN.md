# Zetra

One Thread starts the server

what the server does? accepts connections only

each peer listens for messages and saves them in queue

one thread starts the protocol manager and dispatches the messages from the queue to the correct destination

each protocol is handled in parallel

input maybe? makes no sense

where should I fit the wallet?
