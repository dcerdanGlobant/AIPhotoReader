//
//  ShareViewModel.swift
//  iosApp
//
//  Created by Miguel Ferrer on 5/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp
import UniformTypeIdentifiers

// MARK: - ShareViewModel
final class ShareViewModel: ObservableObject {
    private let info: ShareViewInfo
    private let dependencies = KoinDependency()
    private let getPictureDescription: GetPictureDescription
    private var screenWidth: CGFloat = 400
    
    @Published var image: UIImage?
    @Published var description: String?
    
    init(with info: ShareViewInfo) {
        self.info = info
        getPictureDescription = dependencies.getPictureDescription()
    }
}

// MARK: - Logic
extension ShareViewModel {
    func viewAppeared() {
        loadImage()
    }
    
    func setScreenWidth(_ width: CGFloat) {
        screenWidth = width
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
        _ = info.itemProvider.loadDataRepresentation(for: .image) { [weak self] data, error in
            guard let self else { return }
            if let data, let originalImage = UIImage(data: data), error.isNil {
                let aspectRatio = originalImage.size.height / originalImage.size.width
                let width = screenWidth
                let height = width * aspectRatio
                let size = CGSize(width: width, height: height)
                if let previewImage = originalImage.preparingThumbnail(of: size) {
                    image = previewImage
                    loadDescription()
                } else {
                    description = String.shareImage.noPreviewImage.localized
                }
            } else {
                description = .shareImage.noOriginalImage.localized
            }
        }
    }
    
    func loadDescription() {
        description = nil
        guard let previewImage = image else {
            description = .shareImage.noPreviewImage.localized
            return
        }
        if let kotlinByteArray = getKotlinByteArray(from: previewImage) {
            getPictureDescription.invoke(image: kotlinByteArray, extension: "jpeg") { [weak self] result, error in
                guard let self else { return }
                if let string = result as? String {
                    description = string
                } else {
                    description = .shareImage.noImageDescription.localized
                }
            }
        } else {
            description = .shareImage.noByteArray.localized
        }
    }
    
    func getKotlinByteArray(from image: UIImage) -> KotlinByteArray? {
        guard let imageData = image.jpegData(compressionQuality: 1.0) else { return nil }
        let byteArray = KotlinByteArray(size: Int32(imageData.count))
        for (index, byte) in imageData.enumerated() {
            byteArray.set(index: Int32(index), value: Int8(bitPattern: byte))
        }
        return byteArray
    }
}
