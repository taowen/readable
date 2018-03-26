package example

func sendToPlatforms() {
	httpSend("bloomberg", func(err error) {
		if err == nil {
			increaseCounter("bloomberg_sent", func(err error) {
				if err != nil {
					log("failed to record counter", err)
				}
			})
		} else {
			log("failed to send to bloom berg", err)
		}
	})
	ftpSend("reuters", func(err error) {
		if err == DIRECTORY_NOT_FOUND {
			httpSend("reutersHelp", err)
		}
	})
}

func sendToPlatforms() {
	go sendToBloomberg()
	go sendToReuters()
}

func sendToBloomberg() {
	err := httpSend("bloomberg")
	if err != nil {
		log("failed to send to bloom berg", err)
		return
	}
	err := increaseCounter("bloomberg_sent")
	if err != nil {
		log("failed to record counter", err)
	}
}

func sendToReuters() {
	err := ftpSend("reuters")
	if err == DIRECTORY_NOT_FOUND {
		httpSend("reutersHelp", err)
	}
}