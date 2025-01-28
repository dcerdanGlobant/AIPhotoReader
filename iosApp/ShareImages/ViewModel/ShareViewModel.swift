//
//  ShareViewModel.swift
//  iosApp
//
//  Created by Miguel Ferrer on 5/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import UniformTypeIdentifiers
import ComposeApp

// MARK: - ShareViewModel
final class ShareViewModel: ObservableObject {
    private let info: ShareViewInfo
    
    @Published var image = UIImage()
    @Published var description: String?
        
    init(with info: ShareViewInfo) {
        self.info = info
    }
}

// MARK: - Logic
extension ShareViewModel {
    func viewAppeared() {
        loadImage()
        loadDescription()
    }
    
    func getMoreInfo() {
        loadDescription()
    }
    
    func dismiss() {
        info.extensionContext.completeRequest(returningItems: [])
    }
}

// MARK: - Logic
private extension ShareViewModel {
    func loadImage() {
        info.itemProvider.loadDataRepresentation(forTypeIdentifier: UTType.image.identifier) { data, error in
            guard let data, let image = UIImage(data: data) else { return }
            let aspectRatio = image.size.height / image.size.width
            let width = UIScreen.main.bounds.width - 40
            let height = width * aspectRatio
            let size = CGSize(width: width, height: height)
            guard let preview = image.preparingThumbnail(of: size) else { return }
            self.image = image
        }
    }
    
    func loadDescription() {
        description = nil
        
        guard let image = convertUIImageToByteArray(image: image) else {
            description = "No image"
            return
        }
                
        GetPictureDescriptionWrapper.companion.getPictureDescription(image: image, extension: "jpeg", callback: { result in
                    print("Resultado recibido en Swift: \(result)")

                    switch result {
                    case let success as CommonResultSuccess<Conversation>:
                        let conversation = success.data
                        if let firstMessage = conversation?.messages.first {
                            self.description = firstMessage.content
                        } else {
                            self.description = "No messages found"
                        }

                    case let failure as CommonResult<CommonResultFailure>:
                        self.description = "Error: \(failure)"
                        
                    default:
                        self.description = "Unknown result"
                    }
                })
    }
    
    func convertUIImageToByteArray(image: UIImage) -> KotlinByteArray? {
        var imageData: Data?
        imageData = image.jpegData(compressionQuality: 0.5)
        
        guard let data = imageData else { return nil }

        
        let kotlinByteArray = KotlinByteArray(size: Int32(data.count))
        for (index, byte) in data.enumerated() {
            kotlinByteArray.set(index: Int32(index), value: Int8(bitPattern: byte))
        }
        
        return kotlinByteArray
    }
}
